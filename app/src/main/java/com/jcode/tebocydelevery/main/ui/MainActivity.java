package com.jcode.tebocydelevery.main.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.jcode.tebocydelevery.MyApplication;
import com.jcode.tebocydelevery.MyProfile.ui.MyProfileFragment;
import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.RackProducts.ui.DialogMessageFragment;
import com.jcode.tebocydelevery.RackProducts.ui.RackProductsFragment;
import com.jcode.tebocydelevery.buy.ui.CarBuyActivity;
import com.jcode.tebocydelevery.domain.VolleyApi;
import com.jcode.tebocydelevery.historyOrders.ui.HistoryOrderActivity;
import com.jcode.tebocydelevery.init.login.ui.LoginActivity;
import com.jcode.tebocydelevery.main.MainPresenter;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;
import com.jcode.tebocydelevery.models.User;
import com.jcode.tebocydelevery.myOrders.ui.CancelServicieFragment;
import com.jcode.tebocydelevery.myOrders.ui.GuiStateOrderFragment;
import com.jcode.tebocydelevery.myOrders.ui.OrdersFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView, RackProductsFragment.OnEventRackProductsListener, MyProfileFragment.OnMyProfileInteractionListener, CancelServicieFragment.OnCancelServicieListener {

    public static final int BUY_ACTIVIY_REQUEST = 1;
    public static final int ACTION_REFRESH_ADAPTER = 2;
    public static final int ACTION_NAVIGATION_ORDERS = 3;
    public static final int ACTION_ADD_PRODUCTS = 4;
    private static final String TAG = "MainActivity";
    public static ImageLoader imageLoader;
    public static ArrayList<Product> productsCar;
    MyApplication application;
    @Inject
    MainPresenter presenter;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private ArrayList<Product> productsList;
    private ArrayList<Order> ordersList;
    public static User me;
    private MenuItem itemHistory;
    private MenuItem itemGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        application = (MyApplication) getApplication();

        setupInjection();
        imageLoader = VolleyApi.getInstance(this).getImageLoader();
        productsList = new ArrayList<>();
        productsCar = new ArrayList<>();
        ordersList = new ArrayList<>();
        presenter.subscribeMyProfile();
        fragmentManager = getSupportFragmentManager();
        navigation.setOnNavigationItemSelectedListener(this);
        changeNavigationView(R.id.navigation_home);
    }

    private void setupInjection() {
        application.getMainComponent(this).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribeProductsList();
        presenter.unsubscribeMyProfile();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                //  showItems(false);
                fragment = RackProductsFragment.newInstance(productsList, productsCar);
                getSupportActionBar().setTitle("Lista de Productos");
                break;
            case R.id.navigation_history:
                //showItems(true);
                fragment = OrdersFragment.newInstance();
                getSupportActionBar().setTitle("Lista de Pedidos");
                break;
            case R.id.navigation_account:
                //  Log.d(TAG, "ENTRE A PERFIL");
                //showItems(false);
                fragment = MyProfileFragment.newInstance(me);
                getSupportActionBar().setTitle("Mi Perfil");
                break;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment).commitAllowingStateLoss();
        return true;
    }

    public void showItems(boolean show) {
        itemGuide.setVisible(true);
        itemHistory.setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        itemGuide = menu.findItem(R.id.action_guide);
        itemHistory = menu.findItem(R.id.action_history_order);
        return true;
    }

    public void changeNavigationView(int view) {
        onNavigationItemSelected(navigation.getMenu().findItem(view));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_singout:
                presenter.singOut();
                break;
            case R.id.action_history_order:
                startActivity(new Intent(this, HistoryOrderActivity.class));
                break;
            case R.id.action_guide:
                GuiStateOrderFragment guiStateOrderFragment = GuiStateOrderFragment.newInstance();
                guiStateOrderFragment.show(getSupportFragmentManager(), "Guia");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (productsCar.size() > 0) {
            Toast.makeText(this, "Saliendo", Toast.LENGTH_SHORT).show();
            dialogConfirm("Importante", "Existen productos en el carrito de compras, al salir sus productos seran removidos");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void navigationLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void toCarBuy() {
        if (me != null) {
            if (me.getDni() != null & me.getPhone() != null) {
                navigationToBuyCar();
            } else {
                navigationToProfileUser();
            }
        } else {
            Toast.makeText(this, "Obteniendo información, intente nuevamente", Toast.LENGTH_LONG).show();
        }


    }

    public void navigationToProfileUser() {
        navigation.setSelectedItemId(R.id.navigation_account);
        dialogMessage("Aviso Importante", "Es necesario actualizar su informacion, antes de iniciar una compra");
    }


    public void navigationToBuyCar() {
        Intent intent = new Intent(this, CarBuyActivity.class);
        intent.putParcelableArrayListExtra("products_car_buy", productsCar);
        intent.putExtra("me", me);
        startActivityForResult(intent, BUY_ACTIVIY_REQUEST);
    }

    @Override
    public void dialogMessage(String title, String message) {
        DialogMessageFragment dialogMessageFragment = DialogMessageFragment.newInstance(message, title);
        dialogMessageFragment.show(getSupportFragmentManager(), "DialogMessage");
    }


    @Override
    public void addProdutList(Product product) {
        Boolean find = false;
        for (Product p : productsList) {
            if (product.getId().equals(p.getId())) {
                find = true;
            }
        }

        if (!find) {
            productsList.add(product);
        }
        try {
            ((RackProductsFragment) fragment).updateAdapterList();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    @Override
    public void setUserData(User user) {
        me = user;
        try {
            ((MyProfileFragment) fragment).updateDataUser();
        } catch (Exception e) {

        }
    }

    @Override
    public void updateUser() {
        presenter.updateUser(me);
    }

    @Override
    public void updatePhotoUser(String photo) {
        presenter.updatePhotoUser(photo);
    }

    @Override
    public void showProgressBar(Boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "" + requestCode + " " + resultCode);
        if (requestCode == BUY_ACTIVIY_REQUEST) {
            switch (resultCode) {
                case ACTION_ADD_PRODUCTS:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case ACTION_NAVIGATION_ORDERS:
                    navigation.setSelectedItemId(R.id.navigation_history);

                    break;
                case ACTION_REFRESH_ADAPTER:
                    navigation.setSelectedItemId(R.id.navigation_home);
                    //((RackProductsFragment) fragment).updateAdapterList();
                    break;
            }
        }
    }

    /*
    @Override
    public void toDetailsOrder(Order order) {
        Intent intent = new Intent(this, DetailsOrderActivity.class);
        order.setCliente(me);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    @Override
    public void toCancelOrder(Order order, String reason) {
        presenter.cancelOrder(order, reason);
    }

    @Override
    public void toMoreInfoState(String state, String stateDescription) {
        dialogMessage(state, stateDescription);
    }

    @Override
    public void subscribeMyOrders() {
        presenter.subscribeMyOrders();
    }
*/
    @Override
    public void subscribeProductsList() {
        presenter.subscribeProductsList();
    }


    @Override
    public void updateProdut(Product product) {

        for (Product p : productsList) {
            if (p.getId().equals(product.getId())) {
                p.setContent(product.getContent());
                p.setDescription(product.getDescription());
                if (product.getStock() <= p.getLot()) {
                    p.setLot(product.getStock());
                }
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setStock(product.getStock());
                p.setType(product.getType());
                p.setUrl_photo(product.getUrl_photo());
            }
        }
        /*
        for (int i = 0; i < productsList.size(); i++) {

            if (product.getId().equals(productsList.get(i).getId())) {

                productsList.set(i, product);
            }
        }
        */

        try {
            ((RackProductsFragment) fragment).updateAdapterList();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    @Override
    public void onCancelOrder(String reason, Order order) {
        Log.e(TAG, reason + order.getId_order().toString());
        presenter.cancelOrder(order, reason);
    }


    @Override
    public void showProgressDialog(Boolean show, String message) {
        if (show) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Cancelando Pedido");
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    public void dialogConfirm(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
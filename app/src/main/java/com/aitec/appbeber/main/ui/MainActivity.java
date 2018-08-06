package com.aitec.appbeber.main.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.MyProfile.ui.MyProfileFragment;
import com.aitec.appbeber.R;
import com.aitec.appbeber.RackProducts.ui.DialogMessageFragment;
import com.aitec.appbeber.RackProducts.ui.RackProductsFragment;
import com.aitec.appbeber.buy.ui.CarBuyActivity;
import com.aitec.appbeber.domain.VolleyApi;
import com.aitec.appbeber.main.MainPresenter;
import com.aitec.appbeber.models.Product;
import com.aitec.appbeber.models.User;
import com.aitec.appbeber.myOrders.ui.OrdersFragment;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aitec.appbeber.buy.ui.CompleBuyFragment.MY_PERMISSIONS_REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainView, RackProductsFragment.OnEventRackProductsListener {

    public static final int BUY_ACTIVIY_REQUEST = 1;
    public static final int ACTION_REFRESH_ADAPTER = 2;
    public static final int ACTION_NAVIGATION_ORDERS = 3;
    public static final int ACTION_ADD_PRODUCTS = 4;
    public static boolean navigationCarBuy = false;
    public static final String ACTION_REFRESH_TOKEN = "token";
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
    private ArrayList<Product> productsList;
    public static User me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        application = (MyApplication) getApplication();
        setupInjection();
        productsList = new ArrayList<>();
        productsCar = new ArrayList<>();
        presenter.subscribeMyProfile();
        registreToken();
        navigation.setOnNavigationItemSelectedListener(this);
        changeNavigationView(R.id.navigation_home);
    }

    private void setupInjection() {
        application.getMainComponent(this).inject(this);
    }

    public void registreToken() {
        if (!application.getSharePreferences().getBoolean("token_send", true)) {
            String token = application.getSharePreferences().getString("token", "");
            presenter.registreToken(token);
        }
    }

    @Override
    public void changeStatusToke(boolean sending) {
        application.getSharePreferences().edit().putBoolean("token_send", sending).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
        imageLoader = VolleyApi.getInstance(this).getImageLoader();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment).commitAllowingStateLoss();
        return true;
    }


    public void changeNavigationView(int view) {
        onNavigationItemSelected(navigation.getMenu().findItem(view));
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
                navigationCarBuy = true;
                navigationToProfileUser();
            }
        } else {
            presenter.subscribeMyProfile();
            Toast.makeText(this, "Obteniendo información, intente nuevamente", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkPermissionsExternal() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CODE);
            return false;
        }
    }

    private boolean checkPermissionsFineLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CODE);
            return false;
        }
    }

    public void navigationToProfileUser() {
        navigation.setSelectedItemId(R.id.navigation_account);
        dialogMessage("Aviso Importante", "Es necesario actualizar su informacion, antes de iniciar una compra");
    }

    @Override
    public void navigationToBuyCar() {
        if (checkPermissionsExternal() && checkPermissionsFineLocation()) {
            Intent intent = new Intent(this, CarBuyActivity.class);
            intent.putParcelableArrayListExtra("products_car_buy", productsCar);
            intent.putExtra("me", me);
            startActivityForResult(intent, BUY_ACTIVIY_REQUEST);
        }
    }

    @Override
    public void dialogMessage(String title, String message) {
        DialogMessageFragment dialogMessageFragment = DialogMessageFragment.newInstance(message, title);
        dialogMessageFragment.show(getSupportFragmentManager(), "DialogMessage");
    }


    @Override
    public void setUserData(User user) {
        me = user;
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
                    break;
            }
        }
    }


    @Override
    public void subscribeProductsList() {

    }

    @Override
    public void showProgressDialog(Boolean show, String message) {
        if (show) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    switch (permissions[0]) {
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                                navigationToBuyCar();
                            else
                                Toast.makeText(this, "Permisos necesarios para funcionamiento mostrar el mapa ", Toast.LENGTH_LONG).show();
                            break;
                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                                navigationToBuyCar();
                            else
                                Toast.makeText(this, "Permisos necesarios para obtener la ubicación, a donde enviar su pedido", Toast.LENGTH_LONG).show();
                            break;
                    }

                }
                break;


        }
    }

}
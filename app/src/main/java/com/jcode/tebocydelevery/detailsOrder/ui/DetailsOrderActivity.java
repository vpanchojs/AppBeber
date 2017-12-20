package com.jcode.tebocydelevery.detailsOrder.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jcode.tebocydelevery.MyApplication;
import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.detailsOrder.DetailsOrderPresenter;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;
import com.jcode.tebocydelevery.myOrders.ui.CancelServicieFragment;

import java.util.ArrayList;

import javax.inject.Inject;

public class DetailsOrderActivity extends AppCompatActivity implements DetailsOrderView, CancelServicieFragment.OnCancelServicieListener {

    private static final String TAG = "DetailsOrderActivity";
    MyApplication application;
    @Inject
    DetailsOrderPresenter presenter;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Order order;
    private ProductsOrderFragment productsOrderFragment;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);
        application = (MyApplication) getApplication();
        setupInjection();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalles del Pedido");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        try {
            order = getIntent().getExtras().getParcelable("order");
            order.setProducts(new ArrayList<Product>());
            if (order.getState_order() == Order.ORDER_PREPARADA || order.getState_order() == Order.ORDER_PROCESSANDO) {

            }

        } catch (Exception e) {
            order = new Order();
        }
        presenter.subscribeProductsListOrder(order.getId_order());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void setupInjection() {
        application.getDetailsComponent(this).inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details_order, menu);
        if (order.getState_order() == Order.ORDER_PREPARADA || order.getState_order() == Order.ORDER_PROCESSANDO) {
            MenuItem item = menu.findItem(R.id.action_cancel_order);
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_cancel_order:
                CancelServicieFragment cancelServicieFragment = CancelServicieFragment.nuevaInstancia(order);
                cancelServicieFragment.show(getSupportFragmentManager(), "CancelarOrder");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgressBar(Boolean show) {
        if (show) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Cancelando Pedido");
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setProducts(ArrayList<Product> products) {
        order.getProducts().addAll(products);
        productsOrderFragment.updateAdapter();
    }

    @Override
    public void onCancelOrder(String reason, Order order) {
        presenter.cancelOrder(order, reason);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Fragment fragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = DescriptionOrderFragment.newInstance(order);
                    break;
                case 1:
                    fragment = ProductsOrderFragment.newInstance((ArrayList<Product>) order.getProducts());
                    productsOrderFragment = (ProductsOrderFragment) fragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DESCRIPCIóN";
                case 1:
                    return "PRODUCTOS";
            }
            return null;
        }
    }

    @Override
    public void closeActivity() {
        finish();
    }
}

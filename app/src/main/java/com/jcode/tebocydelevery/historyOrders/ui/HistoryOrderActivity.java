package com.jcode.tebocydelevery.historyOrders.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jcode.tebocydelevery.MyApplication;
import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.RackProducts.ui.DialogMessageFragment;
import com.jcode.tebocydelevery.detailsOrder.ui.DetailsOrderActivity;
import com.jcode.tebocydelevery.historyOrders.HistoryOrderPresenter;
import com.jcode.tebocydelevery.main.ui.MainActivity;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.myOrders.adapters.OrdersAdapter;
import com.jcode.tebocydelevery.myOrders.adapters.onItemClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryOrderActivity extends AppCompatActivity implements HistoryOrderView, onItemClickListener {
    MyApplication application;
    @Inject
    HistoryOrderPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.ll_orders_empity)
    LinearLayout llOrdersEmpity;
    private OrdersAdapter adapter;
    private ArrayList<Order> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        ButterKnife.bind(this);
        application = (MyApplication) getApplication();
        setupInjection();
        toolbar.setTitle("Historial de Pedidos");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = new ArrayList<>();
        presenter.subscribeMyOrders();
        adapter = new OrdersAdapter(data, this, this);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        rvOrders.setAdapter(adapter);

    }

    private void setupInjection() {
        application.getHistoryOrderComponent(this).inject(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribeMyOrders();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgressBar(Boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        if (message != null)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        if (data.size() <= 0)
            showMessageEmpety(true);

    }

    @Override
    public void detailsOrder(Order order) {
        Intent intent = new Intent(this, DetailsOrderActivity.class);
        order.setCliente(MainActivity.me);
        intent.putExtra("order", order);
        startActivity(intent);
    }

    @Override
    public void cancelOrder(Order order) {

    }

    @Override
    public void moreInfoState(String state, String stateDescription) {
        DialogMessageFragment dialogMessageFragment = DialogMessageFragment.newInstance(stateDescription, state);
        dialogMessageFragment.show(getSupportFragmentManager(), "DialogMessage");
    }

    @Override
    public void setOrders(ArrayList<Order> orders) {
        data.addAll(orders);
        adapter.notifyDataSetChanged();
    }

    private void showMessageEmpety(boolean show) {
        if (show)
            llOrdersEmpity.setVisibility(View.VISIBLE);
        else
            llOrdersEmpity.setVisibility(View.GONE);
    }
}

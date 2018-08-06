package com.aitec.appbeber.historyOrders.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.R;
import com.aitec.appbeber.RackProducts.ui.DialogMessageFragment;
import com.aitec.appbeber.detailsOrder.ui.DetailsOrderActivity;
import com.aitec.appbeber.historyOrders.HistoryOrderPresenter;
import com.aitec.appbeber.main.ui.MainActivity;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.myOrders.adapters.OrdersAdapter;
import com.aitec.appbeber.myOrders.adapters.onItemClickListener;

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
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private OrdersAdapter adapter;
    private ArrayList<Order> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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
        if (show) {
            textView2.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.VISIBLE);
        } else {
            textView2.setVisibility(View.GONE);
            btnAdd.setVisibility(View.GONE);
        }
    }
}

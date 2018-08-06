package com.aitec.appbeber.myOrders.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.R;
import com.aitec.appbeber.RackProducts.ui.DialogMessageFragment;
import com.aitec.appbeber.detailsOrder.ui.DetailsOrderActivity;
import com.aitec.appbeber.historyOrders.ui.HistoryOrderActivity;
import com.aitec.appbeber.main.ui.MainActivity;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.myOrders.MyOrdersPresenter;
import com.aitec.appbeber.myOrders.adapters.OrdersAdapter;
import com.aitec.appbeber.myOrders.adapters.onItemClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class OrdersFragment extends Fragment implements MyOrdersView, onItemClickListener {
    MyApplication application;
    @Inject
    MyOrdersPresenter presenter;

    private static final String TAG = "OrderFragment";
    public static final int DETAILS_ORDER_REQUEST = 2;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    Unbinder unbinder;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.textView2)
    TextView textView2;

    private OrdersAdapter adapter;
    private ArrayList<Order> data;
    // private OnMyOrdersInteractionListener listener;


    public OrdersFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new ArrayList<>();
        adapter = new OrdersAdapter(data, this, getContext());
        application = (MyApplication) getActivity().getApplication();
        setHasOptionsMenu(true);
        setupInjection();
    }

    private void setupInjection() {
        application.getMyOrdersComponent(this).inject(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_myorders, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history_order:
                startActivity(new Intent(getContext(), HistoryOrderActivity.class));
                break;
            case R.id.action_guide:
                GuiStateOrderFragment guiStateOrderFragment = GuiStateOrderFragment.newInstance();
                guiStateOrderFragment.show(getChildFragmentManager(), "Guia");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        presenter.onStart();
        presenter.subscribeMyOrders();
        rvOrders.setLayoutManager(manager);
        rvOrders.setAdapter(adapter);
        return view;
    }

    private void showMessageEmpety() {
        if (data.size() > 0) {
            textView2.setVisibility(View.GONE);
            btnAdd.setVisibility(View.GONE);
        } else {
            btnAdd.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.btn_add)
    public void onViewClicked() {

    }

    @Override
    public void detailsOrder(Order order) {
        Intent intent = new Intent(getContext(), DetailsOrderActivity.class);
        order.setCliente(MainActivity.me);
        intent.putExtra("order", order);
        startActivityForResult(intent, DETAILS_ORDER_REQUEST);
    }

    @Override
    public void cancelOrder(Order order) {
        //listener.toCancelOrder(order, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribeMyOrders();
        presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @Override
    public void moreInfoState(String state, String stateDescription) {
        DialogMessageFragment dialogMessageFragment = DialogMessageFragment.newInstance(stateDescription, state);
        dialogMessageFragment.show(getChildFragmentManager(), "DialogMessage");
    }


    @Override
    public void showProgressBar(Boolean show) {
        if (show)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

    @Override
    public void addOrderList(Order order) {
        Log.e(TAG, "nuevo producto " + data.size());
        data.add(order);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateOrder(Order order) {
        for (int i = 0; i < data.size(); i++) {
            if (order.getId_order().equals(data.get(i).getId_order())) {
                if (order.getState_order() == Order.ORDER_ENTREGATE || order.getState_order() == Order.ORDER_CANCELATE) {
                    adapter.removeOrder(order);
                    showMessageEmpety();
                } else {
                    data.set(i, order);
                    adapter.notifyItemChanged(i);
                }
            }
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
        showMessageEmpety();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "vamo a eliminar");
        switch (requestCode) {
            case DETAILS_ORDER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    adapter.removeOrder((Order) data.getExtras().getParcelable("order"));
                    showMessageEmpety();
                }
                break;
        }
    }
}

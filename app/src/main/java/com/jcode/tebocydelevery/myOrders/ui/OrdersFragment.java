package com.jcode.tebocydelevery.myOrders.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jcode.tebocydelevery.MyApplication;
import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.RackProducts.ui.DialogMessageFragment;
import com.jcode.tebocydelevery.detailsOrder.ui.DetailsOrderActivity;
import com.jcode.tebocydelevery.main.ui.MainActivity;
import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.myOrders.MyOrdersPresenter;
import com.jcode.tebocydelevery.myOrders.adapters.OrdersAdapter;
import com.jcode.tebocydelevery.myOrders.adapters.onItemClickListener;

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
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    Unbinder unbinder;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.ll_orders_empity)
    LinearLayout llOrdersEmpity;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

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
        setupInjection();
    }

    private void setupInjection() {
        application.getMyOrdersComponent(this).inject(this);
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

    private void showMessageEmpety(boolean show) {
        if (show)
            llOrdersEmpity.setVisibility(View.VISIBLE);
        else
            llOrdersEmpity.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_add)
    public void onViewClicked() {

    }

    @Override
    public void detailsOrder(Order order) {
        Intent intent = new Intent(getContext(), DetailsOrderActivity.class);
        order.setCliente(MainActivity.me);
        intent.putExtra("order", order);
        startActivity(intent);
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

    /*
    public void updateAdapterList() {
        if (data.size() > 0) {
            showMessageEmpety(false);
        } else {
            showMessageEmpety(true);
        }
        adapter.notifyDataSetChanged();
    }
    */

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
//        adapter.notifyItemInserted(data.size() - 1);
    }

    @Override
    public void updateOrder(Order order) {
        for (int i = 0; i < data.size(); i++) {
            if (order.getId_order().equals(data.get(i).getId_order())) {
                data.set(i, order);
                adapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        if (data.size() <= 0)
            showMessageEmpety(true);
    }
}

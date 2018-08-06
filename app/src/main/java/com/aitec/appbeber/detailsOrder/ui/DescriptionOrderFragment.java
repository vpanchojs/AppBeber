package com.aitec.appbeber.detailsOrder.ui;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aitec.appbeber.R;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.models.User;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DescriptionOrderFragment extends Fragment {
    @BindView(R.id.et_full_name)
    TextInputEditText etFullName;
    @BindView(R.id.et_dni)
    TextInputEditText etDni;
    @BindView(R.id.et_phone)
    TextInputEditText etPhone;
    @BindView(R.id.et_address)
    TextInputEditText etAddress;
    @BindView(R.id.et_address_reference)
    TextInputEditText etAddressReference;
    @BindView(R.id.icon_order)
    ImageView iconOrder;
    @BindView(R.id.et_date_creation)
    TextInputEditText etDateCreation;
    @BindView(R.id.et_method_pay)
    TextInputEditText etMethodPay;
    @BindView(R.id.et_price)
    TextInputEditText etPrice;
    @BindView(R.id.et_state_order)
    TextInputEditText etStateOrder;
    @BindView(R.id.et_rason_state)
    TextInputEditText etRasonState;
    Unbinder unbinder;
    private Order order;

    public DescriptionOrderFragment() {
        // Required empty public constructor
    }

    public static DescriptionOrderFragment newInstance(Order order) {
        DescriptionOrderFragment fragment = new DescriptionOrderFragment();
        Bundle args = new Bundle();
        args.putParcelable("order", order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            order = getArguments().getParcelable("order");
        } catch (Exception e) {
            order = new Order();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        setData();
        return view;
    }

    public void setData() {
        User cliente = order.getCliente();
        etFullName.setText(cliente.getName() + " " + cliente.getLastname());
        etDni.setText(cliente.getDni());
        etPhone.setText(cliente.getPhone());
        etAddress.setText(order.getAddress());
        etAddressReference.setText(order.getAddress_description());
        etPrice.setText("$ " + order.getPrice_total() + "");
        SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy HH:mm", new Locale("ES"));
        etDateCreation.setText(formateador.format(order.getDate_emision()) + "");
        switch (order.getState_order()) {
            case Order.ORDER_PROCESSANDO:
                etStateOrder.setText("Procesando");
                break;
            case Order.ORDER_CANCELATE:
                etStateOrder.setText("Cancelado");
                break;
            case Order.ORDER_ENTREGATE:
                etStateOrder.setText("Entregado");
                break;
            case Order.ORDER_PREPARADA:
                etStateOrder.setText("Preparado");
                break;
            case Order.ORDER_ENVIADA:
                etStateOrder.setText("Enviado");
                break;
        }
        etRasonState.setText(order.getDescription_state_order());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

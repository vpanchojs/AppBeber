package com.aitec.appbeber.buy.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aitec.appbeber.R;
import com.aitec.appbeber.buy.adapters.ResumeProductsAdapter;
import com.aitec.appbeber.buy.adapters.onEventConfirmOrder;
import com.aitec.appbeber.buy.adapters.onEventListener;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.models.Product;
import com.aitec.appbeber.models.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ResumeBuyFragment extends DialogFragment implements DialogInterface.OnShowListener {

    @BindView(R.id.tv_full_name)
    TextView tvFullName;
    @BindView(R.id.tv_dni)
    TextView tvDni;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address_send)
    TextView tvAddressSend;
    @BindView(R.id.tv_references_send)
    TextView tvReferencesSend;
    @BindView(R.id.tv_list_products)
    TextView tvListProducts;
    @BindView(R.id.tv_price_total)
    TextView tvPriceTotal;
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    Unbinder unbinder;
    private Order order;
    private ResumeProductsAdapter adapter;

    private onEventConfirmOrder listener;

    public ResumeBuyFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResumeBuyFragment newInstance(Order order) {
        ResumeBuyFragment fragment = new ResumeBuyFragment();
        Bundle args = new Bundle();
        args.putParcelable("order", order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = getArguments().getParcelable("order");
            adapter = new ResumeProductsAdapter((ArrayList<Product>) order.getProducts());
        } else {
            order = new Order();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_resum_buy, null);
        unbinder = ButterKnife.bind(this, view);
        setData();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnShowListener(this);
        return dialog;
    }

    public void setData() {
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(adapter);
        User cliente = order.getCliente();
        tvFullName.setText(cliente.getName() + " " + cliente.getLastname());
        tvDni.setText(cliente.getDni());
        tvPhone.setText(cliente.getPhone());
        tvAddressSend.setText(order.getAddress());
        tvReferencesSend.setText(order.getAddress_description());
        tvPriceTotal.setText("$ " + order.getPrice_total() + "");
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog dialogo = (AlertDialog) getDialog();
        if (dialogo != null) {
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.orderConfirmation();
                    dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onEventListener) {
            listener = (onEventConfirmOrder) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


}

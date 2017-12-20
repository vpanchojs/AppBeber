package com.jcode.tebocydelevery.myOrders.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.models.Order;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CancelServicieFragment extends DialogFragment implements DialogInterface.OnShowListener {
    @BindView(R.id.rb_products)
    RadioButton rbProducts;
    @BindView(R.id.rb_order)
    RadioButton rbOrder;
    @BindView(R.id.rb_time)
    RadioButton rbTime;
    @BindView(R.id.rg_options)
    RadioGroup rgOptions;
    @BindView(R.id.btn_cancel_servicie)
    Button btnCancelServicie;
    Unbinder unbinder;

    OnCancelServicieListener mCallback;
    private Order order;

    public CancelServicieFragment() {

    }

    public static CancelServicieFragment nuevaInstancia(Order order) {
        CancelServicieFragment def = new CancelServicieFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("order", order);
        def.setArguments(bundle);
        return def;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnCancelServicieListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " debe implementar OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            order = getArguments().getParcelable("order");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //presenter.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        //presenter.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_cancel_order, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnShowListener(this);
        return dialog;
    }


    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog dialogo = (AlertDialog) getDialog();
        if (dialogo != null) {
            btnCancelServicie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedId = rgOptions.getCheckedRadioButtonId();
                    switch (selectedId) {
                        case R.id.rb_products:
                            mCallback.onCancelOrder(rbProducts.getText().toString(), order);
                            break;
                        case R.id.rb_order:
                            mCallback.onCancelOrder(rbOrder.getText().toString(), order);
                            break;
                        case R.id.rb_time:
                            mCallback.onCancelOrder(rbTime.getText().toString(), order);
                            break;
                    }
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

    public interface OnCancelServicieListener {

        void onCancelOrder(String reason, Order order);

    }


}

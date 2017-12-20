package com.jcode.tebocydelevery.RackProducts.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.models.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AddProductFragment extends DialogFragment implements DialogInterface.OnShowListener {

    @BindView(R.id.et_lot)
    EditText etLot;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    @BindView(R.id.et_stock)
    EditText etStock;
    Unbinder unbinder1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    private OnAddProductInteractionListener mListener;
    private int position;
    private Product product;

    public AddProductFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(Product product, int position) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putParcelable("product", product);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            product = getArguments().getParcelable("product");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_options_product, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialogo = (AlertDialog) getDialog();
        if (dialogo != null) {
            etLot.setText(product.getLot() + "");
            etStock.setText(product.getStock() + "");
            tvMessage.setText("Ingrese la cantidad de " + product.getName() + " que desee agregar a su carrito de compras");
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validar()) {
                        ((RackProductsFragment) getParentFragment()).onAddProductLot(Integer.parseInt(etLot.getText().toString()), position);
                        dismiss();
                        //mListener.onAddProductLot(Integer.parseInt(etLot.getText().toString()), position);
                    } else {
                        etLot.setError("Cantidad Erronea");
                    }

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

    public boolean validar() {
        try {
            int lot = Integer.parseInt(etLot.getText().toString());
            return lot <= product.getStock();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof OnAddProductInteractionListener) {
            mListener = (OnAddProductInteractionListener) childFragment;
        } else {
            throw new RuntimeException(childFragment.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface OnAddProductInteractionListener {
        // TODO: Update argument type and name
        void onAddProductLot(int lot, int position);
    }

}

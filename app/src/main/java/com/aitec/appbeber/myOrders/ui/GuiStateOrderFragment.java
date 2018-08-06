package com.aitec.appbeber.myOrders.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.aitec.appbeber.R;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.models.StateOrder;
import com.aitec.appbeber.myOrders.adapters.GuideStateAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GuiStateOrderFragment extends DialogFragment implements DialogInterface.OnShowListener {

    @BindView(R.id.rv_guide_state)
    RecyclerView rvGuideState;
    Unbinder unbinder;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    Unbinder unbinder1;

    public GuiStateOrderFragment() {
    }

    ArrayList<StateOrder> stateOrderList;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GuiStateOrderFragment newInstance() {
        GuiStateOrderFragment fragment = new GuiStateOrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateOrderList = new ArrayList<>();
        stateOrderList.add(new StateOrder(Order.ORDER_PROCESSANDO, "El pedido esta a la espera de ser aceptado. Se puede cancelar el pedido"));
        stateOrderList.add(new StateOrder(Order.ORDER_PREPARADA, "Productos listos para ser enviados, Se puede cancelar el pedido"));
        stateOrderList.add(new StateOrder(Order.ORDER_ENVIADA, "Pedido viajando a su destino"));
        stateOrderList.add(new StateOrder(Order.ORDER_ENTREGATE, "Pedido entregado con éxito"));
        stateOrderList.add(new StateOrder(Order.ORDER_CANCELATE, "Pedido cancelado"));
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_item_list, null);
        unbinder = ButterKnife.bind(this, view);
        rvGuideState.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGuideState.setAdapter(new GuideStateAdapter(stateOrderList, getContext()));
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(final DialogInterface dialog) {
        final AlertDialog dialogo = (AlertDialog) getDialog();
        if (dialogo != null) {
            btnAccept.setOnClickListener(new View.OnClickListener() {
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
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
    */

    /*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 * <p/>
 * See the Android Training lesson <a href=
 * "http://developer.android.com/training/basics/fragments/communicating.html"
 * >Communicating with Other Fragments</a> for more information.
 */
    /*
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
    */


}

package com.jcode.tebocydelevery.RackProducts.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jcode.tebocydelevery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DialogMessageFragment extends DialogFragment implements DialogInterface.OnShowListener {

    public View view;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    Unbinder unbinder;
    private String message;
    private String title;

//    private OnFragmentInteractionListener mListener;

    public DialogMessageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DialogMessageFragment newInstance(String message, String title) {
        DialogMessageFragment fragment = new DialogMessageFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString("message");
            title = getArguments().getString("title");
        }
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_message, null);
        unbinder = ButterKnife.bind(this, view);
        setData();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setOnShowListener(this);
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
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

    public void setData() {
        tvMessage.setText(message);
        tvTitle.setText(title);
    }

    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    */
}

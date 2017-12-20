package com.jcode.tebocydelevery.buy.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.buy.adapters.ProductsAdapter;
import com.jcode.tebocydelevery.buy.adapters.onClickListener;
import com.jcode.tebocydelevery.buy.adapters.onEventListener;
import com.jcode.tebocydelevery.models.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ProductosCarBuyFragment extends Fragment implements onClickListener {
    private static final String TAG = "ProductosCar";
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.ll_car_empity)
    LinearLayout llCarEmpity;
    Unbinder unbinder;
    @BindView(R.id.btn_add)
    Button btnAdd;

    private ProductsAdapter adapter;
    private onEventListener listener;

    private ArrayList<Product> data;
    // TODO: Rename and change types of parameters


    public ProductosCarBuyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductosCarBuyFragment newInstance(ArrayList<Product> data) {
        ProductosCarBuyFragment fragment = new ProductosCarBuyFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("products_car_buy", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getParcelableArrayList("products_car_buy");
            calculatePriceTotal();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productos_car_buy, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (data.size() > 0) {
            llCarEmpity.setVisibility(View.GONE);
        }

        adapter = new ProductsAdapter(data, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProducts.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvProducts.setAdapter(adapter);
        return view;
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
            listener = (onEventListener) context;
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

    @Override
    public void moreLot(int position) {
        Product p = data.get(position);
        if (p.getStock() == p.getLot()) {
            Toast.makeText(getContext(), "Cantidad no disponible", Toast.LENGTH_SHORT).show();
        } else {
            p.setLot(p.getLot() + 1);
            adapter.notifyItemChanged(position);
            calculatePriceTotal();
        }

    }

    @Override
    public void lessLot(int position) {
        Product p = data.get(position);
        if (p.getLot() - 1 > 0) {
            adapter.notifyItemChanged(position);
            p.setLot(p.getLot() - 1);
            calculatePriceTotal();
        } else {
            removeProduct(position, p.getName() + "(" + p.getContent() + ")");
        }
    }

    @Override
    public void moraLotCantidad(String cant, int position) {
        Product p = data.get(position);
        int lot;
        try {
            lot = Integer.parseInt(cant);
            if (p.getStock() < lot) {
                Toast.makeText(getContext(), "Cantidad no disponible", Toast.LENGTH_SHORT).show();
            } else {
                p.setLot(lot);
                adapter.notifyItemChanged(position);
                calculatePriceTotal();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cantidad no validad", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void removeProduct(int position, String s) {
        dialogConfirmRemoveProduct("Remover Producto", "Se removera  " + s + " de su carrito de compras", position);
    }

    public void dialogConfirmRemoveProduct(String title, String message, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("REMOVER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Product p = data.get(position);
                p.setLot(0);
                data.remove(position);
                adapter.notifyDataSetChanged();
                if (data.size() <= 0) {
                    llCarEmpity.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getContext(), "Producto Removido", Toast.LENGTH_SHORT).show();
                calculatePriceTotal();
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void calculatePriceTotal() {
        double price = 0.0;
        for (Product p : data) {
            price = price + (p.getLot() * p.getPrice());
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String price_format = df.format(price).replace(',', '.');
        Log.e(TAG, "El precio de es: " + price_format);
        price = Double.parseDouble(price_format);
        listener.setPriceTotal(price);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        listener.navigationProductList();
    }
}

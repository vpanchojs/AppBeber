package com.jcode.tebocydelevery.RackProducts.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.RackProducts.adapters.ProductsAdapter;
import com.jcode.tebocydelevery.RackProducts.adapters.onItemClickListener;
import com.jcode.tebocydelevery.RackProducts.adapters.onItemLongClickListener;
import com.jcode.tebocydelevery.models.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RackProductsFragment extends Fragment implements onItemClickListener, onItemLongClickListener {
    private static final String TAG = "RackProducts";

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.fab_car_buy)
    FloatingActionButton fabCarBuy;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ProductsAdapter adapter;
    private OnEventRackProductsListener listener;

    private ArrayList<Product> data;
    private ArrayList<Product> products_car;

    public RackProductsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RackProductsFragment newInstance(ArrayList<Product> productsList, ArrayList<Product> products_car) {
        RackProductsFragment fragment = new RackProductsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("products_list", productsList);
        args.putParcelableArrayList("products_car", products_car);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getParcelableArrayList("products_list");
            products_car = getArguments().getParcelableArrayList("products_car");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new ProductsAdapter(data, this, this);
        rvProducts.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProducts.setAdapter(adapter);
        showButtonCarBuy();
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onStart() {
        super.onStart();
       // Log.e(TAG, "start products");
        listener.subscribeProductsList();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick(R.id.fab_car_buy)
    public void onViewClicked() {
        listener.toCarBuy();
    }


    @Override
    public void addProductCarBuy(Product product, int position, TextView tvProductLot) {
        if (product.getStock() > 0 && product.getStock() > product.getLot()) {
            product.setLot(product.getLot() + 1);
            if (product.getLot() == 1) {
                if (!products_car.contains(product)) {
                    products_car.add(product);
                }
            }
            tvProductLot.setText(product.getLot() + "");
            tvProductLot.setVisibility(View.VISIBLE);
            //adapter.notifyItemChanged(position);
        } else {
            showMessage("Cantidad Insuficiente");
        }
    }

    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addProductCarBuyLot(Product product, int position) {
        AddProductFragment addProductFragment = AddProductFragment.newInstance(product, position);
        addProductFragment.show(getChildFragmentManager(), "Resumen");
    }

    /*
    @Override
    public void setCarBuy(List<Product> productList) {
        for (Product p : productList) {
            for (Product p1 : data) {
                if (p.getId().equals(p1.getId())) {
                    p1.setLot(p.getLot());
                    products_car.add(p1);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
*/


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventRackProductsListener) {
            listener = (OnEventRackProductsListener) context;
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


    public void onAddProductLot(int lot, int position) {
        Product product = data.get(position);
        product.setLot(lot);
        if (lot > 0) {
            if (!products_car.contains(product)) {
                products_car.add(product);
            }

            showMessage("Producto Agregado");
        } else {
            products_car.remove(product);
            showMessage("Producto removido");
        }
        adapter.notifyItemChanged(position);


    }

    public void updateAdapterList() {
        adapter.notifyDataSetChanged();
       // Log.e(TAG, "ACTUALIZANDO");
        showButtonCarBuy();
    }

    public void showButtonCarBuy() {
        if (data.size() > 0) {
            fabCarBuy.setVisibility(View.VISIBLE);
        } else {
            fabCarBuy.setVisibility(View.GONE);
        }
    }

    public interface OnEventRackProductsListener {
        void toCarBuy();

        void subscribeProductsList();
    }
}

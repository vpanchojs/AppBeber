package com.jcode.tebocydelevery.detailsOrder.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jcode.tebocydelevery.R;
import com.jcode.tebocydelevery.detailsOrder.adapter.ProductsOrderAdapter;
import com.jcode.tebocydelevery.models.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsOrderFragment extends Fragment {
    private static final String PRODUCTS_LIST = "products_list";
    private static final String TAG = "ProductsOrderFragment";
    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private ArrayList<Product> products;
    private ProductsOrderAdapter adapter;


    public ProductsOrderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProductsOrderFragment newInstance(ArrayList<Product> products) {
        ProductsOrderFragment fragment = new ProductsOrderFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PRODUCTS_LIST, products);
        fragment.setArguments(args);
        Log.e(TAG, "ENTRE");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            products = getArguments().getParcelableArrayList(PRODUCTS_LIST);
            adapter = new ProductsOrderAdapter(products);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProducts.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, products.size() + "");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void updateAdapter() {
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}

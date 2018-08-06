package com.aitec.appbeber.RackProducts.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.R;
import com.aitec.appbeber.RackProducts.RackProductsPresenter;
import com.aitec.appbeber.RackProducts.adapters.ProductsAdapter;
import com.aitec.appbeber.RackProducts.adapters.onItemClickListener;
import com.aitec.appbeber.RackProducts.adapters.onItemLongClickListener;
import com.aitec.appbeber.models.Product;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RackProductsFragment extends Fragment implements onItemClickListener, onItemLongClickListener, RackProductsView, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String TAG = "RackProducts";

    @BindView(R.id.rv_products)
    RecyclerView rvProducts;
    @BindView(R.id.fab_car_buy)
    FloatingActionButton fabCarBuy;
    Unbinder unbinder;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    SearchView searchView;

    MyApplication application;

    @Inject
    RackProductsPresenter presenter;

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
        Log.e(TAG, products_car.size() + "");
        data = new ArrayList<>();
        Log.e(TAG, products_car.size() + "");
        application = (MyApplication) getActivity().getApplication();
        setupInjection();
        setHasOptionsMenu(true);
    }


    private void setupInjection() {
        application.getRackProductsComponent(this).inject(this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_products, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Buscar Productos");
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new ProductsAdapter(data, this, this, getContext());
        presenter.onStart();
        presenter.subscribeProductsList();
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
        presenter.unsubscribeProductsList();
        presenter.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

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

    @Override
    public void showProgressBar(Boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void addProdutList(Product product) {
        for (Product pro : products_car) {
            if (pro.getId().equals(product.getId())) {
                //Asignamos el lot del producto anterior guardado en carrito de compras
                product.setLot(pro.getLot());
                //Eliminamos el producto anterior
                products_car.remove(pro);
                //Agregamos el nuevo producto
                products_car.add(product);
                break;
            }
        }
        data.add(product);
        adapter.notifyDataSetChanged();
        showButtonCarBuy();
    }

    @Override
    public void updateProdut(Product product) {
        for (Product p : data) {
            if (p.getId().equals(product.getId())) {
                p.setContent(product.getContent());
                p.setDescription(product.getDescription());
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                p.setStock(product.getStock());
                p.setType(product.getType());
                p.setUrl_photo(product.getUrl_photo());

                if (product.getStock() <= p.getLot()) {
                    if (product.getStock() <= 0) {
                        products_car.remove(product);
                    }
                    p.setLot(product.getStock());
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.setFilter(filter(s));
        return true;
    }

    @Override
    public boolean onClose() {
        adapter.setFilter(data);
        return true;
    }

    private ArrayList<Product> filter(String query) {
        query = query.toLowerCase();
        ArrayList<Product> productsFilter = new ArrayList<>();
        for (Product p : data) {
            if (p.getName().toLowerCase().startsWith(query)) {
                productsFilter.add(p);
            }

        }
        return productsFilter;
    }
}

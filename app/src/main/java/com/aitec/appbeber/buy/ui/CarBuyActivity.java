package com.aitec.appbeber.buy.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aitec.appbeber.MyApplication;
import com.aitec.appbeber.R;
import com.aitec.appbeber.buy.BuyPresenter;
import com.aitec.appbeber.buy.adapters.onEventConfirmOrder;
import com.aitec.appbeber.buy.adapters.onEventListener;
import com.aitec.appbeber.main.ui.MainActivity;
import com.aitec.appbeber.models.Order;
import com.aitec.appbeber.models.Product;
import com.aitec.appbeber.models.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aitec.appbeber.buy.ui.CompleBuyFragment.MY_PERMISSIONS_REQUEST_CODE;

public class CarBuyActivity extends AppCompatActivity implements onEventListener, onEventConfirmOrder, CarBuyView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "CarbuyActivity";
    private static final double BASE_PRICE = 5.00;
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    /*-------- */
    public static final long UPDATE_INTERVAL = 3000;
    public static final long UPDATE_FASTEST_INTERVAL = UPDATE_INTERVAL / 2;
    /*---------*/
    public static Location mCurrentLocation;
    protected LocationRequest mLocationRequest;
    MyApplication application;
    @Inject
    BuyPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_price_total)
    TextView tvPriceTotal;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.container)
    NonSwipeableViewPage container;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.progress_bar_circle)
    ProgressBar progressBarCircle;
    @BindView(R.id.ll_content_button)
    LinearLayout llContentButton;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    private ArrayList<Product> data;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Order order;
    private User cliente;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_buy);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ButterKnife.bind(this);
        mLocationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest)
                .setAlwaysShow(true);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        buildGoogleApiClient();
        application = (MyApplication) getApplication();
        setupInjection();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Carrito de Compras");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        order = new Order();

        try {
            data = MainActivity.productsCar;
            cliente = getIntent().getExtras().getParcelable("me");
            order.setCliente(cliente);
        } catch (Exception e) {

        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), data);
        container.setAdapter(mSectionsPagerAdapter);

    }


    private void setupInjection() {
        application.getBuyComponent(this).inject(this);
    }


    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }


    private void showDialogGPS() {
        if (mLocationRequest != null && mGoogleApiClient != null) {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
            builder.setAlwaysShow(true); //this is the key ingredient

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient
                    , builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(CarBuyActivity.this, PETICION_CONFIG_UBICACION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        showDialogGPS();
        presenter.onStart();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_car_buy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(MainActivity.ACTION_ADD_PRODUCTS, new Intent());
                finish();
                return true;
            case R.id.action_add_product:
                setResult(MainActivity.ACTION_ADD_PRODUCTS, new Intent());
                finish();
                break;
            case R.id.action_cancel_buy:
                dialogConfirmDeleteBuy("Cancelar Compra", "Se removeran todos los productos de su carrito de compras");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearCar() {
        for (Product p : data) {
            p.setLot(0);
        }
        data.clear();
    }

    @OnClick({R.id.btn_back, R.id.btn_next, R.id.btn_exit, R.id.btn_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                container.setCurrentItem(container.getCurrentItem() - 1);
                progressBar.setProgress(1);
                if (container.getCurrentItem() == 0) {
                    btnExit.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.GONE);
                }
                btnNext.setVisibility(View.VISIBLE);
                btnOrder.setVisibility(View.GONE);

                break;
            case R.id.btn_next:
                progressBar.setProgress(2);
                container.setCurrentItem(container.getCurrentItem() + 1);
                btnExit.setVisibility(View.GONE);
                btnBack.setVisibility(View.VISIBLE);

                if (container.getCurrentItem() == mSectionsPagerAdapter.getCount() - 1) {
                    btnOrder.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                }
                break;

            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_order:
                Log.e(TAG, "Comprando");
                order.setProducts(data);
                if (validateProducts() && validateReferences() && validateAddress()) {
                    ResumeBuyFragment resumeBuyFragment = ResumeBuyFragment.newInstance(order);
                    resumeBuyFragment.show(this.getSupportFragmentManager(), "Resumen");
                }
                break;
        }
    }

    public boolean validateProducts() {
        if (data.size() > 0) {
            if (order.getPrice_total() < BASE_PRICE) {
                showMessage("Las compras se realizan apartir de 5 dolares");
                return false;
            } else {
                return true;
            }
        } else {

            showMessage("Agregue productos");
            return false;
        }
    }


    public boolean validateReferences() {
        if (order.getAddress_description().length() > 6) {
            return true;
        } else {
            showMessage("Referencia debe tener un mínimo de 6 caracteres");
            return false;
        }

    }


    public boolean validateAddress() {

        if (order.getAddress() == null) {
            showMessage("Seleccione su ubicación en el mapa");
            return false;
        } else {
            return true;
        }

    }


    @Override
    public void orderConfirmation() {
        presenter.generateOrder(order);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public void dialogConfirmDeleteBuy(String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                clearCar();
                setResult(MainActivity.ACTION_REFRESH_ADAPTER, new Intent());
                finish();
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void dialogMessage(String title, String message, boolean success) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);

        if (success) {
            builder.setPositiveButton("MIS PEDIDOS", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    navigationToMainOrders();
                }
            });
        } else {
            builder.setPositiveButton("REINTENTAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    presenter.generateOrder(order);
                }
            });
            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {


                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }


    @Override
    public void navigationToMainOrders() {
        clearCar();
        setResult(MainActivity.ACTION_NAVIGATION_ORDERS, new Intent());
        finish();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("MAIN", "CONECTANDO A GOOGLE");
        //Conectado correctamente a Google Play Services
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PETICION_PERMISO_LOCALIZACION);

        } else {
            /*mCurrentLocation =
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    */
            enableLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length == 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "TODO BIEN");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    }
                } else {
                    Log.e(TAG, "TODO mal");
                    Toast.makeText(this, "Permiso denegado, imposible obtener su ubicación", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("MAIN", "Se ha interrumpido la conexión con Google Play Services");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("MAIN", "Error grave al conectar con Google Play Services");
    }

    @Override
    public void setPriceTotal(double priceTotal) {
        order.setPrice_send(0.0);
        order.setPrice_total(priceTotal);
        tvPriceTotal.setText("$ " + priceTotal);
    }

    @Override
    public void navigationProductList() {
        finish();
    }

    @Override
    public void showProgressBar(Boolean show) {
        if (show) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Procesando Pedido");
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, location.getLatitude() + "");
        mCurrentLocation = location;
    }


    /**
     * Metodo para activar la actualizacion de mi ubicacion
     */
    private void enableLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(UPDATE_FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        startLocationUpdates();
    }

    /**
     * Metodo para iniciar la actualizacion de mi ubicacion
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Fragment fragment;
        private ArrayList<Product> data;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Product> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = ProductosCarBuyFragment.newInstance(data);
                    break;
                case 1:
                    fragment = new CompleBuyFragment().newInstance(order);
                    break;

            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }

    }
}

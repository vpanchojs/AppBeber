package com.aitec.appbeber.buy.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aitec.appbeber.R;
import com.aitec.appbeber.domain.VolleyActionListener;
import com.aitec.appbeber.domain.VolleyApi;
import com.aitec.appbeber.models.Order;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleBuyFragment extends Fragment implements OnMapReadyCallback {


    private static final String TAG = "CompleteBuy";
    public static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    @BindView(R.id.et_address)
    TextInputEditText etAddress;
    @BindView(R.id.et_address_reference)
    TextInputEditText etAddressReference;
    Unbinder unbinder;

    @BindView(R.id.mapView)
    MapView mapView;

    MapboxMap mapboxMap;

    Order order;
    @BindView(R.id.ib_my_location)
    FloatingActionButton ibMyLocation;

    public CompleBuyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CompleBuyFragment newInstance(Order order) {
        CompleBuyFragment fragment = new CompleBuyFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), "pk.eyJ1IjoidnBhbmNob2pzIiwiYSI6ImNqN2gzdXdrbzFkejEyeG82Z2IyaDcxazUifQ.LMqRVTqNyzGOtED90lMtZA");
        View view = inflater.inflate(R.layout.fragment_comple_buy, container, false);
        unbinder = ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        etAddressReference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                order.setAddress_description(etAddressReference.getText().toString());
            }
        });


        checkPermissions();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getLocation(Double lati, Double lg) {
        etAddress.setText("Obteniendo ubicacion...");
        final double lat = lati;
        final double lng = lg;
        final double zoom = 20;
        order.setLat(lat);
        order.setLng(lng);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 1000; i++) {

                }
                VolleyApi.getInstance(getContext()).getRouteData(lat, lng, zoom, new VolleyActionListener() {
                    @Override
                    public void onSuccess(Object o) {
                        etAddress.setText(o.toString());
                        order.setAddress(o.toString());
                    }

                    @Override
                    public void onError(String error) {
                        etAddress.setText("No se pudo obtener la ubicacion, intente nuevamente");
                    }
                });
            }
        }).start();
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length == 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "TODO BIEN");

                } else {
                    Log.e(TAG, "TODO mal");
                    Toast.makeText(getContext(), "Permiso denegado, imposible obtener su ubicación", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CODE);
        }
    }

    @OnClick(R.id.ib_my_location)
    public void onViewClicked() {
        if (CarBuyActivity.mCurrentLocation != null) {
            //getLocation(CarBuyActivity.mCurrentLocation.getLatitude(), CarBuyActivity.mCurrentLocation.getLongitude());
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(CarBuyActivity.mCurrentLocation.getLatitude(), CarBuyActivity.mCurrentLocation.getLongitude())) // Sets the new camera position
                    .zoom(17) // Sets the zoom
                    .build(); // Creates a CameraPosition from the builder

            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);

        } else {
            Toast.makeText(getContext(), "Obteniendo su ubicación, intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }

        /*
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CODE);
            */


    }

    @Override
    public void onMapReady(final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setMyLocationEnabled(true);
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(-4.32794, -79.55402)) // Sets the new camera position
                .zoom(17) // Sets the zoom
                .build(); // Creates a CameraPosition from the builder

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);

        mapboxMap.setOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                etAddress.setText("Obteniendo ubicacion...");
                //mapboxMap.getCameraPosition();
                CameraPosition cameraPosition = mapboxMap.getCameraPosition();

                final double lat = cameraPosition.target.getLatitude();
                final double lng = cameraPosition.target.getLongitude();
                final double zoom = cameraPosition.zoom;
                order.setLat(lat);
                order.setLng(lng);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 1000; i++) {
                        }
                        VolleyApi.getInstance(getContext()).getRouteData(lat, lng, zoom, new VolleyActionListener() {
                            @Override
                            public void onSuccess(Object o) {
                                etAddress.setText(o.toString());
                                order.setAddress(o.toString());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    }
                }).start();


            }
        });

    }
}

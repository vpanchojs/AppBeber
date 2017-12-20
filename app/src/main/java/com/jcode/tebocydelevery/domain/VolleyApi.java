package com.jcode.tebocydelevery.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by victor on 12/9/17.
 */

public class VolleyApi {

    /*RUTAS PARA EL CONSUMO DEL WEBSERVICIE*/

    //public final static String WEB_SERVICIE_URL = "https://service.lojaturistica.com/";
    public final static String WEB_SERVICIE_URL = "https://service.noutaxi.com/";
    public final static String CONECTION_LOST = "Error de conexión";
    private final static String AUTENTICATION_USER_PATH = "auth/login";
    private final static String USER_INFORMATION_PATH = "infoUser";
    private final static String UPDATE_USER_PASSWORD_PATH = "updatePassword";
    private final static String GET_HISTORY_SERVICIE_PATH = "getHistorial";
    private static VolleyApi mInstance;
    //private ImageLoader mImageLoader;
    private static Context context;
    private RequestQueue request;
    private ImageLoader imageLoader;

    private VolleyApi(Context context) {
        VolleyApi.context = context;
        request = getRequestQueue();
        imageLoader = new ImageLoader(request,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

    }

    public static synchronized VolleyApi getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyApi(context);
        }
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (request == null) {
            request = Volley.newRequestQueue(context.getApplicationContext());
        }
        return request;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public void getRouteData(double lat, double lng, double zoom, final VolleyActionListener callback) {
        String url = "http://nominatim.openstreetmap.org/reverse?email=vpanchojs@gmail.com&format=json&addressdetails=0&zoom=" + (int) zoom + "&lat=" + lat + "&lon=" + lng;


        JsonObjectRequest a = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Respuesta consulta ruta", response.toString());

                        try {
                            String[] address_complete = response.getString("display_name").split(",");
                            String address = address_complete[0] + "," + address_complete[1];
                            callback.onSuccess(address);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores
                        Log.e("Respuesta consulta ruta", error.toString());

                    }
                });

        request.add(a);
    }


}

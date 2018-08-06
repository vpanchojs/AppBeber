package com.aitec.appbeber.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by victor on 5/9/17.
 */
@IgnoreExtraProperties
public class Order implements Parcelable {


    public static final int ORDER_PROCESSANDO = 0;
    public static final int ORDER_PREPARADA = 1;
    public static final int ORDER_ENVIADA = 2;
    public static final int ORDER_ENTREGATE = 3;
    public static final int ORDER_CANCELATE = 4;
    public static final int PAY_EFECTIVO = 0;

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel pc) {
            return new Order(pc);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    private String id_client;
    private String id_vendor;
    private String id_order;
    @Exclude
    private User cliente;
    @Exclude
    private User vendedor;
    private Date date_emision;
    private double price_send;
    private double price_total;
    private double lat;
    private double lng;
    private String address;
    private String address_description = "";
    private int state_order;
    private int method_pay;
    private String description_state_order;
    private List<Product> products;

    /*
    public List<Map<String, Object>> toProductsOrder() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Product p : products) {
            mapList.add(p.toMapProductsOrder());
        }
        return mapList;
    }
    */


    public Order() {
    }

    public Order(String id_order, User cliente, User vendedor, String dateTime, double price_send, double price_total, double lat, double lng, String address, String address_description, int state_order, List<Product> products) {
        this.id_order = id_order;
        this.cliente = cliente;
        this.vendedor = vendedor;
        //this.dateTime = dateTime;
        this.price_send = price_send;
        this.price_total = price_total;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.address_description = address_description;
        this.state_order = state_order;
        this.products = products;
    }

    public Order(Parcel in) {
        this.id_order = in.readString();
        this.cliente = in.readParcelable(User.class.getClassLoader());
        this.vendedor = in.readParcelable(User.class.getClassLoader());
        this.date_emision = (Date) in.readSerializable();
        this.price_send = in.readDouble();
        this.price_total = in.readDouble();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.address = in.readString();
        this.address_description = in.readString();
        this.state_order = in.readInt();
        this.products = in.readArrayList(Product.class.getClassLoader());
        this.description_state_order = in.readString();
        this.method_pay = in.readInt();

    }

    @Exclude
    public Map<String, Object> toMapOrder() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cliente",cliente.toMapUserOrder());
        //result.put(cliente.getId_user().toString(), true);
        result.put("price_send", price_send);
        result.put("price_total", price_total);
        result.put("lat", lat);
        result.put("lng", lng);
        result.put("address", address);
        result.put("address_description", address_description);
        result.put("state_order", state_order);
        result.put("date_emision", FieldValue.serverTimestamp());
        result.put("description_state_order", description_state_order);
        result.put("method_pay", method_pay);
        return result;
    }

    public User getCliente() {
        return cliente;
    }

    public void setCliente(User cliente) {
        this.cliente = cliente;
    }

    public User getVendedor() {
        return vendedor;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public double getPrice_send() {
        return price_send;
    }

    public void setPrice_send(double price_send) {
        this.price_send = price_send;
    }

    public double getPrice_total() {
        return price_total;
    }

    public void setPrice_total(double price_total) {
        this.price_total = price_total;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_description() {
        return address_description;
    }

    public void setAddress_description(String address_description) {
        this.address_description = address_description;
    }

    public int getState_order() {
        return state_order;
    }

    public void setState_order(int state_order) {
        this.state_order = state_order;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getDate_emision() {
        return date_emision;
    }

    public void setDate_emision(Date date_emision) {
        this.date_emision = date_emision;
    }

    public int getMethod_pay() {
        return method_pay;
    }

    public void setMethod_pay(int method_pay) {
        this.method_pay = method_pay;
    }

    public String getDescription_state_order() {
        return description_state_order;
    }

    public void setDescription_state_order(String description_state_order) {
        this.description_state_order = description_state_order;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_order);
        dest.writeParcelable(cliente, flags);
        dest.writeParcelable(cliente, flags);
        dest.writeSerializable(date_emision);
        dest.writeDouble(price_send);
        dest.writeDouble(price_total);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
        dest.writeString(address_description);
        dest.writeInt(state_order);
        dest.writeList(products);
        dest.writeString(description_state_order);
        dest.writeInt(method_pay);
    }

}

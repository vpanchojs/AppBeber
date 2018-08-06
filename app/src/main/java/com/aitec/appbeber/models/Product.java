package com.aitec.appbeber.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Product implements Parcelable {

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel pc) {
            return new Product(pc);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    private String id;
    private String name;
    private String content;
    private double price;
    private int stock;
    private int lot;
    private String url_photo;
    private int type;

    public Product(String id, String name, String content, double price, int stock, String url_photo, int type) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.url_photo = url_photo;
        this.type = type;
    }

    public Product() {
    }

    public Product(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.content = in.readString();
        this.price = in.readDouble();
        this.stock = in.readInt();
        this.url_photo = in.readString();
        this.type = in.readInt();
        this.lot = in.readInt();
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return content;
    }

    public void setDescription(String description) {
        this.content = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Exclude
    public Map<String, Object> toMapProductsOrder() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lot", lot);
        result.put("price", lot * price);
        result.put("name", name);
        result.put("content", content);
        result.put("url_photo", url_photo);
        result.put("type", type);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapProducts() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lot", lot);
        result.put("price", price);
        result.put("name", name);
        result.put("content", content);
        result.put("url_photo", url_photo);
        result.put("type", type);
        result.put("stock", stock);
        return result;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(content);
        dest.writeDouble(price);
        dest.writeInt(stock);
        dest.writeString(url_photo);
        dest.writeInt(type);
        dest.writeInt(lot);
    }

}

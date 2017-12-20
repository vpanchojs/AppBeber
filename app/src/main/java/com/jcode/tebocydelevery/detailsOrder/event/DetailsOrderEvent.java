package com.jcode.tebocydelevery.detailsOrder.event;

import java.util.ArrayList;

/**
 * Created by victor on 29/9/17.
 */

public class DetailsOrderEvent {

    public final static int onProductsReadSuccess = 0;
    public final static int onProductsReadError = 1;
    public final static int onOrderCancelSuccess = 14;
    public final static int onOrderCancelError = 15;


    private String menssage;
    private int event;
    private Object object;
    private ArrayList<Object> objectList;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ArrayList<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(ArrayList<Object> objectList) {
        this.objectList = objectList;
    }


    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }
}

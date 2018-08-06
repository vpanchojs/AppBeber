package com.aitec.appbeber.buy.events;

import java.util.ArrayList;

/**
 * Created by victor on 9/9/17.
 */

public class BuyEvents {

    public final static int onChangeLotSuccess = 0;
    public final static int onChangeLotError = 1;
    public final static int onDeleteProductOrderSuccess = 2;
    public final static int onDeleteProductOrderError = 3;
    public final static int onGenerateOrderSuccess = 4;
    public final static int onGenerateOrderError = 5;


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
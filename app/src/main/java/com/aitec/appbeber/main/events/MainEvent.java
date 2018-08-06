package com.aitec.appbeber.main.events;

import java.util.List;

/**
 * Created by victor on 5/9/17.
 */

public class MainEvent {
    public final static int onSignOutError = 1;
    public final static int onSignOutSucces = 0;

    public final static int onProductReadSuccess = 2;
    public final static int onProductChangeSuccess = 20;
    public final static int onProductReadError = 3;

    public final static int onOrderReadSuccess = 4;
    public final static int onOrderChangeSuccess = 40;
    public final static int onOrderReadError = 5;
    public final static int onOrderCancelSuccess = 14;
    public final static int onOrderCancelError = 15;

    public final static int onGetUserSuccess = 6;
    public final static int onGetUserError = 7;
    public final static int onUpdatePhotoSuccess = 8;
    public final static int onUpdatePhotoError = 9;
    public final static int onUpdateUserSuccess = 10;
    public final static int onUpdateUserError = 11;
    public final static int onUpdateTokenSuccess = 100;
    public final static int onUpdateTokenError = 101;


    private String menssage;
    private int event;
    private Object object;
    private List<Object> objectList;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
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

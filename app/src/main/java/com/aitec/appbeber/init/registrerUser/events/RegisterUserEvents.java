package com.aitec.appbeber.init.registrerUser.events;

/**
 * Created by victo on 19/01/2017.
 */

public class RegisterUserEvents {
    public final static int onSucces = 0;
    public final static int onError = 1;

    private String menssage;
    private int event;

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

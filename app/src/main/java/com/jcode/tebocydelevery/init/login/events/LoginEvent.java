package com.jcode.tebocydelevery.init.login.events;

/**
 * Created by victo on 25/09/2016.
 */

public class LoginEvent {
    public final static int onSiginError = 0;
    public final static int onSigUpError = 1;
    public final static int onSigInSuccess = 2;
    public final static int onSigUpSuccess = 3;
    public final static int onRecoverySuccess = 4;
    public final static int onRecoveryError = 5;
    public final static int login_facebook = 100;
    public final static int login_google = 101;
    public final static int onSigUp = 6;

    private int evenType;
    private String erroMessage;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEvenType() {
        return evenType;
    }

    public void setEvenType(int evenType) {
        this.evenType = evenType;
    }

    public String getErroMessage() {
        return erroMessage;
    }

    public void setErroMessage(String erroMessage) {
        this.erroMessage = erroMessage;
    }
}

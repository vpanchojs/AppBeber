package com.jcode.tebocydelevery.models;

/**
 * Created by victor on 17/12/17.
 */

public class StateOrder {
    private int state;
    private String descripcion;

    public StateOrder(int state, String descripcion) {
        this.state = state;
        this.descripcion = descripcion;
    }

    public StateOrder() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

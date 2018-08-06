package com.aitec.appbeber.buy.adapters;

/**
 * Created by victor on 15/9/17.
 */

public interface onClickListener {

    void moreLot(int position);

    void lessLot(int position);

    void moraLotCantidad(String cant, int position);

    void removeProduct(int position, String s);

}

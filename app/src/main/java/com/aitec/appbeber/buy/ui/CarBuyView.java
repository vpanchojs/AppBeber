package com.aitec.appbeber.buy.ui;

/**
 * Created by victor on 15/9/17.
 */

public interface CarBuyView {
    void showMessage(String message);

    void navigationToMainOrders();

    void dialogMessage(String title, String message, boolean success);

    void showProgressBar(Boolean show);
}

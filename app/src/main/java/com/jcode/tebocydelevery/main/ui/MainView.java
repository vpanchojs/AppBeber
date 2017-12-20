package com.jcode.tebocydelevery.main.ui;

import com.jcode.tebocydelevery.models.Order;
import com.jcode.tebocydelevery.models.Product;
import com.jcode.tebocydelevery.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface MainView {

    void showProgressBar(Boolean show);

    void navigationLogin();

    void showMessage(String message);

    void addProdutList(Product product);

    void updateProdut(Product product);

    void setUserData(User user);

    void dialogMessage(String title, String message);

    void showProgressDialog(Boolean show, String message);
}

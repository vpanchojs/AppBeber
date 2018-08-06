package com.aitec.appbeber.main.ui;

import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface MainView {

    void showProgressBar(Boolean show);

    void showMessage(String message);

    void setUserData(User user);

    void navigationToBuyCar();

    void dialogMessage(String title, String message);

    void showProgressDialog(Boolean show, String message);

    void changeStatusToke(boolean sending);
}

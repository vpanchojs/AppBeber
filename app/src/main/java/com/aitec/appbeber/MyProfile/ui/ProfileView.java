package com.aitec.appbeber.MyProfile.ui;

import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface ProfileView {

    void showProgressBar(Boolean show);

    void navigationLogin();

    void showMessage(String message);

    void setUserData(User user);

    void navigationToBuyCar();

    void dialogMessage(String title, String message);

    void setPhotoUrl();

    void showProgressDialog(Boolean show, String message);

//    void changeStatusToke(boolean sending);
}

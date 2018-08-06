package com.aitec.appbeber.init.login.ui;

/**
 * Created by victo on 24/09/2016.
 */

public interface LoginView {

    void showProgress(boolean show);

    void navigateToMainScreen();

    void navigateToRegisterUser();

    void showMessage(String message);

    void cleanAccount();
}

package com.aitec.appbeber.lib.base;

/**
 * Created by victo on 25/09/2016.
 */

public interface EventBus {
    void registrer(Object suscriber);

    void unregistrer(Object suscriber);

    void post(Object suscriber);
}

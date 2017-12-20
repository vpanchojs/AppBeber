package com.jcode.tebocydelevery.lib;


import com.jcode.tebocydelevery.lib.base.EventBus;


/**
 * Created by victo on 25/09/2016.
 */

public class EventBusImp implements EventBus {
    private org.greenrobot.eventbus.EventBus eventBus;


    public EventBusImp(org.greenrobot.eventbus.EventBus eventBus) {
        this.eventBus = eventBus;
    }


    @Override
    public void registrer(Object suscriber) {
        eventBus.register(suscriber);
    }

    @Override
    public void unregistrer(Object suscriber) {
        eventBus.unregister(suscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
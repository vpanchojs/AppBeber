package com.aitec.appbeber.lib;


import com.aitec.appbeber.lib.base.EventBus;


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
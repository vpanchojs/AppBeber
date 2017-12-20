package com.jcode.tebocydelevery.myOrders.di;

import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.lib.di.LibModule;
import com.jcode.tebocydelevery.myOrders.ui.OrdersFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 17/12/17.
 */
@Singleton
@Component(modules = {LibModule.class, DomainModule.class, MyOrdersModule.class, MyApplicationModule.class})
public interface MyOrdersComponent {
    void inject(OrdersFragment fragment);
}

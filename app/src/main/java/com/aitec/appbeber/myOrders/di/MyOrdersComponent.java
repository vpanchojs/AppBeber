package com.aitec.appbeber.myOrders.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.lib.di.LibModule;
import com.aitec.appbeber.myOrders.ui.OrdersFragment;

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

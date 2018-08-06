package com.aitec.appbeber.RackProducts.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.RackProducts.ui.RackProductsFragment;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.lib.di.LibModule;
import com.aitec.appbeber.myOrders.ui.OrdersFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 17/12/17.
 */
@Singleton
@Component(modules = {LibModule.class, DomainModule.class, RackProductsModule.class, MyApplicationModule.class})
public interface RackProductsComponent {
    void inject(RackProductsFragment fragment);
}

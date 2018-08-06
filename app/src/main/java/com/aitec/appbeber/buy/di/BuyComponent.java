package com.aitec.appbeber.buy.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.buy.ui.CarBuyActivity;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.lib.di.LibModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 9/9/17.
 */

@Singleton
@Component(modules = {LibModule.class, DomainModule.class, BuyModule.class, MyApplicationModule.class})
public interface BuyComponent {
    void inject(CarBuyActivity activity);
}

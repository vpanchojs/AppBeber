package com.jcode.tebocydelevery.buy.di;

import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.buy.ui.CarBuyActivity;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.lib.di.LibModule;

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

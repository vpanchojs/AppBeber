package com.jcode.tebocydelevery.detailsOrder.di;

import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.detailsOrder.ui.DetailsOrderActivity;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.lib.di.LibModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 29/9/17.
 */

@Singleton
@Component(modules = {LibModule.class, DomainModule.class, DetailsOrderModule.class, MyApplicationModule.class})
public interface DetailsComponent {
    void inject(DetailsOrderActivity activity);
}

package com.aitec.appbeber.detailsOrder.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.detailsOrder.ui.DetailsOrderActivity;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.lib.di.LibModule;

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

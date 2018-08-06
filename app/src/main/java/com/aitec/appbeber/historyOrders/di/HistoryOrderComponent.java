package com.aitec.appbeber.historyOrders.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.historyOrders.ui.HistoryOrderActivity;
import com.aitec.appbeber.lib.di.LibModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 17/12/17.
 */
@Singleton
@Component(modules = {LibModule.class, DomainModule.class, HistoryOrderModule.class, MyApplicationModule.class})
public interface HistoryOrderComponent {
    void inject(HistoryOrderActivity activity);
}

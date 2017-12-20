package com.jcode.tebocydelevery.historyOrders.di;

import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.historyOrders.ui.HistoryOrderActivity;
import com.jcode.tebocydelevery.lib.di.LibModule;

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

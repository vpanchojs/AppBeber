package com.jcode.tebocydelevery.init.registrerUser.di;

import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.init.registrerUser.ui.RegistrerUserActivity;
import com.jcode.tebocydelevery.lib.di.LibModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victo on 16/03/2017.
 */
@Singleton
@Component(modules = {LibModule.class, DomainModule.class, RegistreUserModule.class, MyApplicationModule.class})
public interface RegisterUserComponent {
    void inject(RegistrerUserActivity activity);
}

package com.jcode.tebocydelevery.init.login.di;


import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.init.login.ui.LoginActivity;
import com.jcode.tebocydelevery.lib.di.LibModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victo on 16/03/2017.
 */

@Singleton
@Component(modules = {LibModule.class, DomainModule.class, LoginModule.class, MyApplicationModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);

}

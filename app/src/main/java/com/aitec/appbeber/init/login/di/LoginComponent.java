package com.aitec.appbeber.init.login.di;


import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.init.login.ui.LoginActivity;
import com.aitec.appbeber.lib.di.LibModule;

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

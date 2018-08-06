package com.aitec.appbeber.init.registrerUser.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.init.registrerUser.ui.RegistrerUserActivity;
import com.aitec.appbeber.lib.di.LibModule;

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

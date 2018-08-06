package com.aitec.appbeber.main.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.lib.di.LibModule;
import com.aitec.appbeber.main.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 5/9/17.
 */

@Singleton
@Component(modules = {LibModule.class, DomainModule.class, MainModule.class, MyApplicationModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}

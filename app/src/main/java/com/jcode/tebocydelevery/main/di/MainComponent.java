package com.jcode.tebocydelevery.main.di;

import com.jcode.tebocydelevery.MyApplicationModule;
import com.jcode.tebocydelevery.domain.di.DomainModule;
import com.jcode.tebocydelevery.lib.di.LibModule;
import com.jcode.tebocydelevery.main.ui.MainActivity;

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

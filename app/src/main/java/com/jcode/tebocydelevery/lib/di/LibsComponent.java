package com.jcode.tebocydelevery.lib.di;

import com.jcode.tebocydelevery.MyApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victo on 15/03/2017.
 */

@Singleton
@Component(modules = {LibModule.class, MyApplicationModule.class})
public interface LibsComponent {

}

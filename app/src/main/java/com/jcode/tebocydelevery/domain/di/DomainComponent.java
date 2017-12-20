package com.jcode.tebocydelevery.domain.di;

import com.jcode.tebocydelevery.MyApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victo on 15/03/2017.
 */

@Singleton
@Component(modules = {DomainModule.class, MyApplicationModule.class})
public interface DomainComponent {

}

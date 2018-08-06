package com.aitec.appbeber.MyProfile.di;

import com.aitec.appbeber.MyApplicationModule;
import com.aitec.appbeber.MyProfile.ui.MyProfileFragment;
import com.aitec.appbeber.domain.di.DomainModule;
import com.aitec.appbeber.lib.di.LibModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victor on 5/9/17.
 */

@Singleton
@Component(modules = {LibModule.class, DomainModule.class, ProfileModule.class, MyApplicationModule.class})
public interface ProfileComponent {
    void inject(MyProfileFragment fragment);
}

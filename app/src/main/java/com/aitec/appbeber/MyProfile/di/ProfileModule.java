package com.aitec.appbeber.MyProfile.di;

import com.aitec.appbeber.MyProfile.ProfileInteractor;
import com.aitec.appbeber.MyProfile.ProfileInteractorImp;
import com.aitec.appbeber.MyProfile.ProfilePresenter;
import com.aitec.appbeber.MyProfile.ProfilePresenterImp;
import com.aitec.appbeber.MyProfile.ProfileRepository;
import com.aitec.appbeber.MyProfile.ProfileRepositoryImp;
import com.aitec.appbeber.MyProfile.ui.ProfileView;
import com.aitec.appbeber.domain.FirebaseApi;
import com.aitec.appbeber.lib.base.EventBus;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victor on 5/9/17.
 */

@Module
public class ProfileModule {

    private ProfileView view;

    public ProfileModule(ProfileView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    ProfileView providesWelcomeView() {
        return this.view;
    }

    @Provides
    @Singleton
    ProfilePresenter providesProfilePresenter(EventBus eventBus, ProfileView view, ProfileInteractor interactor) {
        return new ProfilePresenterImp(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    ProfileInteractor providesProfileInteractor(ProfileRepository repository) {
        return new ProfileInteractorImp(repository);
    }

    @Provides
    @Singleton
    ProfileRepository providesProfileRepository(EventBus eventBus, FirebaseApi firebaseApi) {
        return new ProfileRepositoryImp(eventBus, firebaseApi);
    }
}

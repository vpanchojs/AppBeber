package com.aitec.appbeber.MyProfile;

import android.util.Log;

import com.aitec.appbeber.MyProfile.event.ProfileEvent;
import com.aitec.appbeber.MyProfile.ui.ProfileView;
import com.aitec.appbeber.lib.base.EventBus;
import com.aitec.appbeber.main.ui.MainActivity;
import com.aitec.appbeber.models.User;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by victor on 5/9/17.
 */

public class ProfilePresenterImp implements ProfilePresenter {

    private EventBus eventBus;
    private ProfileView view;
    private ProfileInteractor interactor;


    public ProfilePresenterImp(EventBus eventBus, ProfileView view, ProfileInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onStart() {
        eventBus.registrer(this);
    }

    @Override
    public void onStop() {
        eventBus.unregistrer(this);
    }


    @Override
    public void singOut() {
        interactor.singOut();
    }


    @Override
    public void subscribeMyProfile() {
        view.showProgressBar(true);
        interactor.subscribeMyProfile();
    }

    @Override
    public void unsubscribeMyProfile() {
        interactor.unsubscribeMyProfile();
    }

    @Override
    public void updateUser(User user) {
        view.showProgressDialog(true, "Actualizando Usuario");
        interactor.updateUser(user);
    }

    @Override
    public void updatePhotoUser(String photo) {
        view.showProgressDialog(true, "Actualizando foto de perfil");
        interactor.updatePhotoUser(photo);
    }


    @Subscribe
    @Override
    public void onEventMainThread(ProfileEvent event) {
        Log.e("ProfilePresenter", "si funco");
        view.showProgressBar(false);
        switch (event.getEvent()) {
            case ProfileEvent.onSignOutSucces:
                view.navigationLogin();
                break;
            case ProfileEvent.onSignOutError:
                view.showMessage(event.getMenssage());
                break;
            case ProfileEvent.onGetUserSuccess:
                view.setUserData((User) event.getObject());
                break;
            case ProfileEvent.onGetUserError:
                view.showMessage(event.getMenssage());
                break;
            case ProfileEvent.onUpdateUserSuccess:
                view.showProgressDialog(false, "");
                view.showMessage(event.getMenssage());
                if (MainActivity.navigationCarBuy) {
                    view.navigationToBuyCar();
                    MainActivity.navigationCarBuy = false;
                }
                break;
            case ProfileEvent.onUpdateUserError:
                view.showProgressDialog(false, "");
                view.showMessage(event.getMenssage());
                break;
            case ProfileEvent.onUpdatePhotoSuccess:
                view.showProgressDialog(false, "");
                view.showMessage(event.getMenssage());
                break;
            case ProfileEvent.onUpdatePhotoError:
                view.showProgressDialog(false, "");
                view.showMessage(event.getMenssage());
                break;
        }
    }


}

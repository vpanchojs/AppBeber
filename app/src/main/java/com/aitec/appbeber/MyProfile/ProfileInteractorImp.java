package com.aitec.appbeber.MyProfile;

import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public class ProfileInteractorImp implements ProfileInteractor {
    private ProfileRepository repository;

    public ProfileInteractorImp(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public void singOut() {
        repository.singOut();
    }

    @Override
    public void subscribeMyProfile() {
        repository.subscribeMyProfile();
    }

    @Override
    public void updatePhotoUser(String photo) {
        repository.updatePhotoUser(photo);
    }

    @Override
    public void unsubscribeMyProfile() {
        repository.unsubscribeMyProfile();
    }

    @Override
    public void updateUser(User user) {
        repository.updateUser(user);
    }

}

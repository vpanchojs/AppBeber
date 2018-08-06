package com.aitec.appbeber.MyProfile;

import com.aitec.appbeber.MyProfile.event.ProfileEvent;
import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface ProfilePresenter {

    void onDestroy();

    void onStart();

    void onStop();

    void subscribeMyProfile();

    void unsubscribeMyProfile();

    void updateUser(User user);

    void updatePhotoUser(String photo);

    void singOut();

    void onEventMainThread(ProfileEvent event);
}

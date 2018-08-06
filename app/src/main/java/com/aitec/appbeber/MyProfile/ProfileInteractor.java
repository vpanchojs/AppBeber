package com.aitec.appbeber.MyProfile;

import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface ProfileInteractor {

    void singOut();

    void subscribeMyProfile();

    void updatePhotoUser(String photo);

    void unsubscribeMyProfile();

    void updateUser(User user);

}

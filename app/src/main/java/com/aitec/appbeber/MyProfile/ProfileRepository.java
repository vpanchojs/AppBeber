package com.aitec.appbeber.MyProfile;

import com.aitec.appbeber.models.User;

/**
 * Created by victor on 5/9/17.
 */

public interface ProfileRepository {
    void singOut();

    void subscribeMyProfile();

    void unsubscribeMyProfile();

    void updateUser(User user);

    void updatePhotoUser(String photo);

}

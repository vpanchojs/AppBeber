package com.jcode.tebocydelevery.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.jcode.tebocydelevery.domain.FirebaseApi;
import com.jcode.tebocydelevery.domain.Util;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victo on 15/03/2017.
 */

@Module
public class DomainModule {

    public DomainModule() {

    }

    @Provides
    @Singleton
    FirebaseApi providesFirebaseAPI() {
        return new FirebaseApi();
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder) {
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context) {
        return new Geocoder(context);
    }


}

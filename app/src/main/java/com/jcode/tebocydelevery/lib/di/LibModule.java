package com.jcode.tebocydelevery.lib.di;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jcode.tebocydelevery.lib.EventBusImp;
import com.jcode.tebocydelevery.lib.GlideImageLoader;
import com.jcode.tebocydelevery.lib.base.EventBus;
import com.jcode.tebocydelevery.lib.base.ImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victo on 14/03/2017.
 */

@Module
public class LibModule {

    private Activity activity;

    public LibModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    Activity providesFragment() {
        return this.activity;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager() {
        return Glide.with(activity);
    }


    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new EventBusImp(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();
    }


}

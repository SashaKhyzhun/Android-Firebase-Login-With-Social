package com.sashakhyzhun.android_firebase_login_with_social;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;

/**
 * Created by SashaKhyzhun on 1/2/17.
 */

public class AndroidFirebaseLoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }


}

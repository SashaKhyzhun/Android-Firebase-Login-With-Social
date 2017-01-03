package com.sashakhyzhun.android_firebase_login_with_social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SashaKhyzhun on 1/2/17.
 */

public class SplashActivity extends AppCompatActivity {

    private boolean isLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (isLogin)
            startActivity(new Intent(this, MainActivity.class));
        else
            startActivity(new Intent(this, LoginActivity.class));


    }
}

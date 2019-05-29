package com.example.trafficsigns.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.trafficsigns.HomeActivity;
import com.example.trafficsigns.R;


public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(splashThread, SPLASH_TIME_OUT);
    }

    private Runnable splashThread = new Runnable() {

        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    };
}

package com.example.mydairyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.mydairyapp.object.HttpVolleyConn;

public class SplashActivity extends HttpVolleyConn {
    ImageView splashImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImg = findViewById(R.id.image_splash);

        handler.sendEmptyMessageDelayed(0, 2000);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        splashImg.startAnimation(animation);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();

        }
    };
}

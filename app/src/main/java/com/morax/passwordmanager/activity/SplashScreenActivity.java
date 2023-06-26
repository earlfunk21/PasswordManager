package com.morax.passwordmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.morax.passwordmanager.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        ImageView ivLogo = findViewById(R.id.iv_logo);
        TextView tvAppName = findViewById(R.id.tv_app_name_splash_screen);
        TextView tvDescription = findViewById(R.id.tv_description);

        Animation animLogo = AnimationUtils.loadAnimation(this, R.anim.splash_screen_logo);
        Animation animText = AnimationUtils.loadAnimation(this, R.anim.splash_screen_text);

        ivLogo.setAnimation(animLogo);
        tvAppName.setAnimation(animText);
        tvDescription.setAnimation(animText);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.studybuddy.login.LoginActivity;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo, splashImage;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        splashImage = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.animation);

        splashImage.animate().translationY(-2500).setDuration(1000).setStartDelay(3000);
        logo.animate().translationY(1900).setDuration(1000).setStartDelay(3000);
        lottieAnimationView.animate().translationY(1000).setDuration(1000).setStartDelay(3000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                // Sending Email to Dashboard Activity using intent.
                startActivity(intent);
            }
        }, 4000);   //4 seconds



    }
}
package com.example.studybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

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

    }
}
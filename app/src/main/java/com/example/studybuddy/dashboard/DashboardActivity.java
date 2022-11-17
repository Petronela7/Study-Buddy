package com.example.studybuddy.dashboard;

import static com.example.studybuddy.login.LoginFragment.UserEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.studybuddy.R;
import com.example.studybuddy.SearchActivity2;
import com.example.studybuddy.timer.TimerActivity;

public class DashboardActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    TextView hello, email;

    CardView cardAllSessions;

    CardView cardMySessions;

    CardView cardCreateSession;
    CardView cardStatistics;

    CardView cardPomodoro;

    CardView cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        lottieAnimationView = findViewById(R.id.animation);
        lottieAnimationView.animate().setDuration(1000);

        hello = findViewById(R.id.textHello);
        email = findViewById(R.id.textEmail);
        email.setText(getIntent().getStringExtra(UserEmail));

        cardMySessions = findViewById(R.id.my_session);

        cardAllSessions = findViewById(R.id.all_sessions);

        cardCreateSession = findViewById(R.id.create_session);

        cardStatistics = findViewById(R.id.statistics);

        cardPomodoro = findViewById(R.id.pomodoro);

        cardLogout = findViewById(R.id.logout);



        cardMySessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("These are your sessions");
            }
        });

        cardPomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TimerActivity.class);
                // Sending Email to Dashboard Activity using intent.
                startActivity(intent);
            }
        });

        cardAllSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchActivity2.class);
                // Sending Email to Dashboard Activity using intent.
                startActivity(intent);
            }
        });


        //https://www.youtube.com/watch?v=x7jkLrgeJTY
    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
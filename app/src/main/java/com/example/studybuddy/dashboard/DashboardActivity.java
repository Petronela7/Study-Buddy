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
import com.example.studybuddy.allSessions.AllSessionsActivity;
import com.example.studybuddy.createSession.MapActivity;
import com.example.studybuddy.model.User;
import com.example.studybuddy.mySessions.MySessions;
import com.example.studybuddy.timer.TimerActivity;

import java.io.Serializable;

public class DashboardActivity extends AppCompatActivity implements Serializable {

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
        User user = (User) getIntent().getSerializableExtra("KEY_EMAIL");
        email.setText(user.getUserEmail());

        cardMySessions = findViewById(R.id.my_session);

        cardAllSessions = findViewById(R.id.all_sessions);

        cardCreateSession = findViewById(R.id.create_session);

        cardStatistics = findViewById(R.id.statistics);

        cardPomodoro = findViewById(R.id.pomodoro);

        cardLogout = findViewById(R.id.logout);



        cardMySessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MySessions.class);
                // Sending Email to Dashboard Activity using intent.
                intent.putExtra("KEY_USER",user);
                startActivity(intent);
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
                Intent intent = new Intent(getBaseContext(), AllSessionsActivity.class);
                // Sending Email to Dashboard Activity using intent.
                intent.putExtra("KEY_USER",user);
                startActivity(intent);
            }
        });
        cardCreateSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
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
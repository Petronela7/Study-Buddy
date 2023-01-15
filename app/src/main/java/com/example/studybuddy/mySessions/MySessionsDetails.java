package com.example.studybuddy.mySessions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.example.studybuddy.allSessions.AllSessionsActivity;
import com.example.studybuddy.allSessions.SessionDetails;
import com.example.studybuddy.database.SQLiteHelper;
import com.example.studybuddy.model.Session;
import com.example.studybuddy.model.User;

public class MySessionsDetails extends AppCompatActivity {
    SQLiteHelper myDB;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sessions_details);

        myDB = new SQLiteHelper(this);

        User user = (User) getIntent().getSerializableExtra("KEY_USER");
        userEmail = user.getUserEmail();

        Session session = (Session) getIntent().getSerializableExtra("KEY_NAME");
        TextView textViewName = findViewById(R.id.textView6);
        TextView textViewSubject = findViewById(R.id.textViewSubject);
        TextView textViewYear = findViewById(R.id.textViewYear);
        TextView textViewLocation = findViewById(R.id.textViewLocation);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewTime = findViewById(R.id.textViewTime);

        textViewName.setText(session.getSessionName());
        textViewSubject.setText("Group name: " + session.getSessionGroup());
        textViewYear.setText("Year: " + session.getSessionYear());
        textViewLocation.setText("Location: " + session.getSessionLocation());
        textViewDate.setText("Date: " + session.getSessionStartDate());
        textViewTime.setText("Time: " + session.getSessionStartTime());
    }
}
package com.example.studybuddy.allSessions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.example.studybuddy.dashboard.DashboardActivity;
import com.example.studybuddy.database.SQLiteHelper;
import com.example.studybuddy.login.LoginFragment;
import com.example.studybuddy.model.Session;
import com.example.studybuddy.model.User;

import java.io.Serializable;

public class SessionDetails extends AppCompatActivity implements Serializable {

    Button btnJoin;
    SQLiteHelper myDB;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

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

        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = myDB.findUserIdByEmail(userEmail);
                boolean ans = false;

                if(userId != -1) {
                    ans = myDB.joinSession(userId, session.getSessionId());
                }

                if (ans) {
                    Toast.makeText(SessionDetails.this,"Joined!!!!",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SessionDetails.this,"You already joined this session!",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), AllSessionsActivity.class);
                startActivity(intent);
            }
        });


    }
}
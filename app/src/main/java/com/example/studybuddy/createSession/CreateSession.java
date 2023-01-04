package com.example.studybuddy.createSession;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studybuddy.R;
import com.example.studybuddy.SearchActivity2;

public class CreateSession extends AppCompatActivity {

    TextView location;
    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        location = findViewById(R.id.locationTextView);
        //location.setText("we are here");
        mapButton = findViewById(R.id.button_for_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                // Sending Email to Dashboard Activity using intent.
                startActivity(intent);
            }
        });

    }
}
package com.example.studybuddy.statistics;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studybuddy.R;
import com.example.studybuddy.database.SQLiteHelper;

public class Statistics extends AppCompatActivity {

    SQLiteHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        myDB = new SQLiteHelper(this);
        Cursor data = myDB.selectUS();
        int count = data.getCount();

        TextView textView = findViewById(R.id.textView10);

        textView.setText("There are a total of " + count + " joined sessions in StuddyBuddy App!");

    }
}
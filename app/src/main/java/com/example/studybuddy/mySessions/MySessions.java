package com.example.studybuddy.mySessions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.example.studybuddy.allSessions.AllSessionsActivity;
import com.example.studybuddy.dashboard.DashboardActivity;
import com.example.studybuddy.database.SQLiteHelper;
import com.example.studybuddy.model.Session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySessions extends AppCompatActivity implements Serializable {
    SQLiteHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sessions);

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new SQLiteHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.selectAllUserSessions();

        if(data.getCount() == 0) {
            Toast.makeText(MySessions.this,"You don't have sessions in the database!",Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()) {
                //string(0) = session & string(1) = userid
                theList.add(data.getString(0) + "   -   " + data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }
    }
}
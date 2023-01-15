package com.example.studybuddy.allSessions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.example.studybuddy.database.SQLiteHelper;
import com.example.studybuddy.model.Session;
import com.example.studybuddy.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSessionsActivity extends AppCompatActivity implements Serializable {
    SQLiteHelper myDB;
    List<Session> sessionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sessions);

        User user = (User) getIntent().getSerializableExtra("KEY_USER");

        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new SQLiteHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.selectAllSessions();

        if(data.getCount() == 0) {
            Toast.makeText(AllSessionsActivity.this,"You don't have sessions in the database!",Toast.LENGTH_LONG).show();
        } else {
            while(data.moveToNext()) {
                theList.add(data.getString(1) + "   -   " + data.getString(4));
                Session s = new Session(Integer.parseInt(data.getString(0)),data.getString(1),data.getString(2),data.getString(3),
                        data.getString(4),data.getString(5),data.getString(6));
                sessionList.add(s);
                ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AllSessionsActivity.this, SessionDetails.class);
                intent.putExtra("KEY_NAME", sessionList.get(i));
                intent.putExtra("KEY_USER", user);
                startActivity(intent);
            }
        });

    }


}
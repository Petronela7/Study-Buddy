package com.example.studybuddy.mySessions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studybuddy.R;
import com.example.studybuddy.allSessions.AllSessionsActivity;
import com.example.studybuddy.allSessions.SessionDetails;
import com.example.studybuddy.database.SQLiteHelper;
import com.example.studybuddy.model.Session;
import com.example.studybuddy.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySessions extends AppCompatActivity implements Serializable {
    SQLiteHelper myDB;
    List<Session> mySessionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sessions);

        User user = (User) getIntent().getSerializableExtra("KEY_USER");

        ListView listView = (ListView) findViewById(R.id.listView1);
        myDB = new SQLiteHelper(this);

        ArrayList<String> theList = new ArrayList<>();
        int userId = myDB.findUserIdByEmail(user.getUserEmail());

//        Cursor data;
        if(userId!=-1){
            Cursor data = myDB.selectAllUserSessions(userId);
//            if(data.getCount() == 0) {
//                Toast.makeText(MySessions.this,"You don't have sessions in the database!",Toast.LENGTH_LONG).show();
//            } else {
                while(data.moveToNext()) {
                    Session session = myDB.findSessionById(Integer.parseInt(data.getString(1)));
                    theList.add(session.getSessionName());
                    mySessionList.add(session);
                    ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, theList);
                    listView.setAdapter(listAdapter);
                }
//            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MySessions.this, MySessionsDetails.class);
                    intent.putExtra("KEY_NAME", mySessionList.get(i));
                    intent.putExtra("KEY_USER", user);
                    startActivity(intent);
                }
            });
        }

    }
}
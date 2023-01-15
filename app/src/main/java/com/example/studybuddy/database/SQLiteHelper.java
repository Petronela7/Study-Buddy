package com.example.studybuddy.database;

/**
 * Created by Petronela Halip
 */

import static java.security.AccessController.getContext;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.studybuddy.allSessions.SessionDetails;
import com.example.studybuddy.login.LoginFragment;
import com.example.studybuddy.model.Session;

import java.io.File;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="StudyBuddyDB.db";

    public static final String USER_TABLE_NAME="User";
    public static final String User_ID="ID_User";
    public static final String User_Name="Name";
    public static final String User_Email="Email";
    public static final String User_Password="Password";
    public static final String User_Mobile="Mobile";

    public static final String SESSION_TABLE_NAME="Session";
    public static final String Session_ID="ID_Session";
    public static final String Session_Name=       "Name";
    public static final String Session_Subject=    "Subject";
    public static final String Session_Year=       "Year_Of_Study";
    public static final String Session_Date_Start= "Date_Start";
    public static final String Session_Time_Start= "Time_Start";
    public static final String Session_Location=   "Location";

    public static final String USER_SESSION_TABLE_NAME="User_Session";

    String query;


    public SQLiteHelper(Context context) {


        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE_USER="CREATE TABLE IF NOT EXISTS "+USER_TABLE_NAME+
                " ("+User_ID+" INTEGER PRIMARY KEY, "+User_Name+" VARCHAR, "+User_Email+" VARCHAR, "+User_Mobile+" VARCHAR, "
                +User_Password+" VARCHAR)";
        database.execSQL(CREATE_TABLE_USER);

        String CREATE_TABLE_SESSION="CREATE TABLE IF NOT EXISTS "+SESSION_TABLE_NAME+
                " ("+Session_ID+" INTEGER PRIMARY KEY, "+Session_Name+" VARCHAR, "+Session_Subject+" VARCHAR," +
                ""+Session_Year+" INT, "+Session_Date_Start+" VARCHAR, "+Session_Time_Start+" VARCHAR, "+Session_Location+" VARCHAR)";
        database.execSQL(CREATE_TABLE_SESSION);

        String CREATE_TABLE_USER_SESSION="CREATE TABLE IF NOT EXISTS " +USER_SESSION_TABLE_NAME + " ("
                + User_ID+" INTEGER, "
                + Session_ID + " INTEGER, "
                + "FOREIGN KEY ( " +User_ID + ") REFERENCES " + USER_TABLE_NAME + "( " + User_ID +"), "
                + "FOREIGN KEY ( " +Session_ID+ ") REFERENCES " + SESSION_TABLE_NAME+ "( " + Session_ID +"), "
                + "CONSTRAINT PK_UserSes PRIMARY KEY (" +User_ID + ", "+Session_ID + "))";

        database.execSQL(CREATE_TABLE_USER_SESSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+SESSION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+USER_SESSION_TABLE_NAME);
        onCreate(db);

    }

    public Cursor selectAllSessions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + SESSION_TABLE_NAME, null);
        return data;
    }


    public boolean joinSession(int sessionId, int userId) {
        String checkExists = "SELECT * FROM " + USER_SESSION_TABLE_NAME + " WHERE " + User_ID + "=? AND " + Session_ID + "=?";
        String insertUserSession = "INSERT INTO " + USER_SESSION_TABLE_NAME + " (" + User_ID + "," + Session_ID + ") VALUES (" + userId +
                "," + sessionId + ");";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(checkExists, new String[]{String.valueOf(userId),String.valueOf(sessionId)});

        if (data.getCount() == 0) {
            db.execSQL(insertUserSession);
            return true;
        } else {
            return false;
        }
    }

    public int findUserIdByEmail(String email) {
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + User_Email + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query, new String[]{email});
        int id = -1;

        if (data.getCount() == 1) {
            if(data.moveToNext()) {
                id = Integer.parseInt(data.getString(0));
            }
        }
        return id;

    }

    public Session findSessionById(int sessionId){
        String query = "SELECT * FROM " + SESSION_TABLE_NAME + " WHERE " + Session_ID + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query, new String[]{String.valueOf(sessionId)});

        Session session = null;
        if (data.getCount() != 0) {
            if(data.moveToNext()) {
                session = new Session(Integer.parseInt(data.getString(0)),data.getString(1),data.getString(2),data.getString(3),
                        data.getString(4),data.getString(5),data.getString(6));
            }
        }
        return session;
    }

    public Cursor selectMyUserSessions(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + USER_SESSION_TABLE_NAME + " WHERE " + User_ID + "=? AND " + Session_ID + ">?";
        Cursor data = db.rawQuery(select, new String[]{String.valueOf(userId),"0"});
        return data;
    }

    public Cursor selectUS() {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + USER_SESSION_TABLE_NAME;
        Cursor data = db.rawQuery(select, null);
        return data;
    }
}

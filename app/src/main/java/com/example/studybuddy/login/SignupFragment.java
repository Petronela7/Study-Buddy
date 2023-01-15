package com.example.studybuddy.login;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import static com.example.studybuddy.database.SQLiteHelper.DATABASE_NAME;
import static com.example.studybuddy.database.SQLiteHelper.USER_TABLE_NAME;
import static com.example.studybuddy.database.SQLiteHelper.User_Email;
import static com.example.studybuddy.database.SQLiteHelper.User_ID;
import static com.example.studybuddy.database.SQLiteHelper.User_Mobile;
import static com.example.studybuddy.database.SQLiteHelper.User_Name;
import static com.example.studybuddy.database.SQLiteHelper.User_Password;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studybuddy.IntroductoryActivity;
import com.example.studybuddy.R;
import com.example.studybuddy.dashboard.DashboardActivity;
import com.example.studybuddy.database.SQLiteHelper;
import com.example.studybuddy.login.LoginActivity;
import com.example.studybuddy.model.User;

import java.io.File;

public class SignupFragment extends Fragment {
    EditText email, password, mobile, confirm_password;
    Button signup;
    float v = 0;
    public static final String UserEmail = "KEY_EMAIL";

    String  EmailHolder, PasswordHolder, ConfirmPassHolder, Mobile;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    public static User user = new User();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_fragment, container, false);

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        mobile= root.findViewById(R.id.mobile);
        signup = root.findViewById(R.id.signup_button);
        confirm_password = root.findViewById(R.id.confirm_password);

        email.setTranslationX(800);
        password.setTranslationX(800);
        mobile.setTranslationX(800);
        signup.setTranslationX(800);
        confirm_password.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        mobile.setAlpha(v);
        signup.setAlpha(v);
        confirm_password.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        mobile.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        confirm_password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        sqLiteHelper = new SQLiteHelper(getContext());
        // Adding click listener to register button.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating SQLite database if dose n't exists
                SQLiteDataBaseBuild();
                // Creating SQLite table if dose n't exists.
                SQLiteTableBuild();
                // Checking EditText is empty or Not.
                CheckEditTextStatus();
                //Method to check whether ot=r not the email is a valid stud.ubbcluj.ro mail
                //checkingUbbClujMail();
                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();
                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();
            }
        });

        return root;
    }

    // SQLite database build method.
    public void SQLiteDataBaseBuild(){
        File dbpath = getContext().getDatabasePath(SQLiteHelper.DATABASE_NAME);
        if (!dbpath.getParentFile().exists()) {
            dbpath.getParentFile().mkdirs();
        }
        sqLiteDatabaseObj = openOrCreateDatabase(dbpath, null);
    }
    // SQLite table build method.
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS "+USER_TABLE_NAME+" ("+User_ID+" INTEGER PRIMARY KEY,"
                +User_Email+" VARCHAR, "+User_Mobile+" VARCHAR, "+User_Password+" VARCHAR)");

    }
    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {
            user.setUserEmail(email.getText().toString());
            user.setUserPassword(password.getText().toString());
            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+ USER_TABLE_NAME+" (email,mobile,password) VALUES('"+EmailHolder+"', '"+Mobile+"', '"+PasswordHolder+"');";
            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            // Closing SQLite database object.
            sqLiteDatabaseObj.close();
            // Printing toast message after done inserting.
            Toast.makeText(getContext(),"User Registered Successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), DashboardActivity.class);
            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, user);
            startActivity(intent);
        }
        // This block will execute if any of the registration EditText is empty.
        else {
            // Printing toast message if any of EditText is empty.
            Toast.makeText(getContext(),"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }
    }
    // Empty edittext after done inserting process method.
    public void EmptyEditTextAfterDataInsert(){
        email.getText().clear();
        mobile.getText().clear();
        password.getText().clear();
        confirm_password.getText().clear();
    }
    // Method to check EditText is empty or Not.
    public void CheckEditTextStatus(){
        // Getting value from All EditText and storing into String Variables.
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        Mobile = mobile.getText().toString();

        if(TextUtils.isEmpty(Mobile) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }
    public void checkingUbbClujMail(){
        EmailHolder = email.getText().toString().trim();
        // onClick of button perform this simplest code.
        if (!EmailHolder.matches("[a-zA-Z0-9._-]+@stud\\.ubbcluj\\.ro"))
        {
            Toast.makeText(getContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
        }

    }
    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.USER_TABLE_NAME, null, " " + User_Email + "=?", new String[]{EmailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";
                // Closing cursor.
                cursor.close();
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();
    }
    // Checking result
    public void CheckFinalResult(){
        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            // If email is exists then toast msg will display.
            Toast.makeText(getContext(),"Email Already Exists", Toast.LENGTH_LONG).show();
        }
        else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }


}

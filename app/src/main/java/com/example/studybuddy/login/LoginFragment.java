package com.example.studybuddy.login;

import android.annotation.SuppressLint;
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

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.studybuddy.IntroductoryActivity;
import com.example.studybuddy.MainActivity;
import com.example.studybuddy.R;
import com.example.studybuddy.dashboard.DashboardActivity;
import com.example.studybuddy.database.SQLiteHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment {
    EditText email, password;
    TextView forgetPass;
    Button login;
    float v = 0;

    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);

        sqLiteHelper = new SQLiteHelper(getContext());


        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        forgetPass = root.findViewById(R.id.forgot_password);
        login = root.findViewById(R.id.login_button);

        email.setTranslationX(800);
        password.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        forgetPass.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        //Adding click listener to log in button.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling EditText is empty or no method.
                CheckEditTextStatus();
                // Calling login method.
                LoginFunction();
            }
        });

        return root;

    }
    // Login function starts from here.
    @SuppressLint("Range")
    public void LoginFunction(){
        if(EditTextEmptyHolder) {
            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.USER_TABLE_NAME, null, " " + SQLiteHelper.User_Email + "=?", new String[]{EmailHolder}, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.User_Password));
                    // Closing cursor.
                    cursor.close();
                }
            }
            // Calling method to check final result ..
            CheckFinalResult();
        }
        else {
            //If any of login EditText empty then this block will be executed.
            Toast.makeText(getContext(),"Please Enter UserName or Password.", Toast.LENGTH_LONG).show();
        }
    }
    // Checking EditText is empty or not.
    public void CheckEditTextStatus(){
        // Getting value from All EditText and storing into String Variables.
        EmailHolder = email.getText().toString();
        PasswordHolder = password.getText().toString();
        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }
    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_LONG).show();
            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(getContext(), DashboardActivity.class);
            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(getContext(),"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
    }


}

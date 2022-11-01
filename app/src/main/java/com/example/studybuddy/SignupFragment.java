package com.example.studybuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupFragment extends Fragment {
    EditText email, password, mobile, confirm_password;
    Button signup;
    float v = 0;

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
        return root;
    }

}

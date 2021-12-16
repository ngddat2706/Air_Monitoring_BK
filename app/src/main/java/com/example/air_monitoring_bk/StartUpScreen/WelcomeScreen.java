package com.example.air_monitoring_bk.StartUpScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.air_monitoring_bk.Admin.Login;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeScreen extends AppCompatActivity {

    public static boolean Tran = true;
    Button Admin;
    Button User;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Admin = findViewById(R.id.AdminBtn);
        User = findViewById(R.id.UserBtn);
        Tran = false;

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = MainActivity.mAuth.getCurrentUser();
                if(mUser == null){
                    startActivity(new Intent(WelcomeScreen.this, Login.class));
                }
                else{
                    startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
                }
            }
        });

        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
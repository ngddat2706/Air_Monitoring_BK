package com.example.air_monitoring_bk.Admin;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.air_monitoring_bk.Admin0.AdminItem;
import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.example.air_monitoring_bk.StartUpScreen.WelcomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private static  final String FILE_NAME = "myFile";
    ImageView LoginBackBtn;
    public static TextInputEditText LoginEmail;
    TextInputEditText LoginPass;
    Button LoginBtn;
    ProgressDialog progressDialog;
    public static Boolean checkLogin = false;
    public static Integer indexAdmin;
    private CheckBox remeberMe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginBackBtn = findViewById(R.id.login_back_button);
        LoginEmail = findViewById(R.id.login_email);
        LoginPass = findViewById(R.id.login_password);
        LoginBtn = findViewById(R.id.login_button);
        progressDialog = new ProgressDialog(this);
        remeberMe = findViewById(R.id.remember_me);

        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String username = preferences.getString("username","");
        String password = preferences.getString("password","");
        LoginEmail.setText(username);
        LoginPass.setText(password);

        LoginBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(Login.this, WelcomeScreen.class));
                onBackPressed();
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginAdmin();
                String username = LoginEmail.getText().toString().trim();
                String password = LoginPass.getText().toString().trim();
                if(remeberMe.isChecked()){
                    StoreAccountLogin(username, password);
                }
                LoginAdmin();

            }
        });

    }

    private void StoreAccountLogin(String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME,MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.putString("password",password);
        editor.apply();
    }


    private void loginAdmin() {
        String email = LoginEmail.getText().toString();
        String password = LoginPass.getText().toString();
        progressDialog.show();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Login.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            LoginEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            LoginPass.requestFocus();
        }else{
            MainActivity.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "Admin logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finishAffinity();
                    }else {
                        Toast.makeText(Login.this, "Log in Error" + task.getException()
                                .getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void LoginAdmin(){
        String email = LoginEmail.getText().toString().trim();
        String password = LoginPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(Login.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            LoginEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            LoginPass.requestFocus();
        }else{
            progressDialog.show();
            MainActivity.mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "Admin0 logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finishAffinity();
                    }else {
                        for(int i = 0; i<MainActivity.Admin_List.size(); i++){
                            if(MainActivity.Admin_List.get(i).getEmail().contains(email)){
                                progressDialog.dismiss();
                                if(MainActivity.Admin_List.get(i).getPassword().contains(password)){
                                    indexAdmin = i;
                                    Toast.makeText(Login.this, "Admin logged in successfully", Toast.LENGTH_SHORT).show();
                                    checkLogin = true;
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finishAffinity();
                                    break;
                                }
                            }
                        }
                        if(!checkLogin)
                            Toast.makeText(Login.this, "Log in Error" + task.getException()
                                .getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Toast.makeText(Login.this, "Wrong Email" ,Toast.LENGTH_SHORT).show();
        }
    }

}

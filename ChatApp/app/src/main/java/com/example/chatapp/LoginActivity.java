package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Toolbar actionbarLogin;
    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnRegisterLogin;

    private FirebaseAuth auth;
    private FirebaseUser currentUser;

    public void init() {
         actionbarLogin = (Toolbar) findViewById(R.id.actionbarLogin);
         setSupportActionBar(actionbarLogin);
         getSupportActionBar().setTitle("GiRİŞ YAP");


         txtEmail = (EditText) findViewById(R.id.txtEmailLogin);
         txtPassword = (EditText) findViewById(R.id.txtPasswordLogin);
         btnLogin = (Button) findViewById(R.id.btnLogin);
         btnRegisterLogin = (Button) findViewById(R.id.btnRegisterLogin);

         auth=FirebaseAuth.getInstance();
         currentUser=auth.getCurrentUser();



    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToRegisterActivity();
            }
        });
    }

    private void goToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }

    private void loginUser() {

        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"EMAİL ALANI BOŞ OLAMAZ",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"ŞİFRE ALANI BOŞ OLAMAZ",Toast.LENGTH_LONG).show();
        }else{

            btnLogin.setEnabled(false);

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"GİRİŞ BAŞARILI",Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity2.class);
                        startActivity(mainIntent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"GİRİŞ BAŞARISIZ",Toast.LENGTH_LONG).show();
                        btnLogin.setEnabled(true);
                    }

                }
            });

        }
    }
}
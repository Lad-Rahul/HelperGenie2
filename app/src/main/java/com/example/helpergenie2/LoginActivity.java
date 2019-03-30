package com.example.helpergenie2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText InputEmail,InputPasswd;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup,btnLogin,btnForgetpasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            Toast.makeText(getApplicationContext(),"Already Logged in",Toast.LENGTH_LONG).show();
            finish();
        }

        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        InputEmail = (EditText)findViewById(R.id.email_login);
        InputPasswd = (EditText)findViewById(R.id.passwd_login);
        progressBar = (ProgressBar)findViewById(R.id.progressBar_login);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnSignup = (Button)findViewById(R.id.btn_gosignup);
        btnForgetpasswd = (Button)findViewById(R.id.btn_forgotpassword);

        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        btnForgetpasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = InputEmail.getText().toString();
                final String password = InputPasswd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){

                            if(password.length()<6) {
                                InputPasswd.setError(getString(R.string.minimum_password));
                            }
                            else{
                                Toast.makeText(LoginActivity.this,getString(R.string.auth_failed),Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Sucessfully Logged in",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                });

            }
        });

    }
}

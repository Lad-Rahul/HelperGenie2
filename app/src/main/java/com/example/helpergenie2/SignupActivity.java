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

public class SignupActivity extends AppCompatActivity {

    private EditText InputEmail,InputPasswd;
    private Button btnGoLogin,btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup = (Button)findViewById(R.id.btn_signup);
        btnGoLogin = (Button)findViewById(R.id.btn_gologin);
        InputEmail = (EditText) findViewById(R.id.email);
        InputPasswd = (EditText) findViewById(R.id.passwd);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = InputEmail.getText().toString().trim();
                String passwd = InputPasswd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter Email Address",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(passwd)){
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(passwd.length() < 6){
                    Toast.makeText(getApplicationContext(),"Password is too short",Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);
                //Create User
                auth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(SignupActivity.this,"createUserWithEmail:onComplete" + task.isSuccessful(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Authantication Failed" + task.getException(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }

        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}

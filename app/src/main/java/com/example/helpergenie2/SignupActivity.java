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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText InputEmail,InputPasswd;
    private Button btnGoLogin,btnSignup;
    private ProgressBar progressBar;


    private EditText Fmobile,Fadd1,Fadd2,Fpin,Fname;
    //private TextView FnameText;
    private FirebaseAuth auth;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;

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

        mData = FirebaseDatabase.getInstance();
        Fname = (EditText)findViewById(R.id.fname);
        Fmobile = (EditText)findViewById(R.id.fmobile);
        Fadd1 = (EditText)findViewById(R.id.fadd1);
        Fadd2 = (EditText)findViewById(R.id.fadd2);
        Fpin = (EditText)findViewById(R.id.fpin);

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

                            String tname = Fname.getText().toString();
                            String tmobile = Fmobile.getText().toString();
                            String tadd1 = Fadd1.getText().toString();
                            String tadd2 = Fadd2.getText().toString();
                            String tpin = Fpin.getText().toString();
                            if( tname.matches("")){
                                Toast.makeText(SignupActivity.this,"Write Name",Toast.LENGTH_SHORT).show();
                            }
                            else if( tmobile.matches("")){
                                Toast.makeText(SignupActivity.this,"Write Mobile No",Toast.LENGTH_SHORT).show();
                            }
                            else if(tadd1.matches("")){
                                Toast.makeText(SignupActivity.this,"Write Address line 1",Toast.LENGTH_SHORT).show();
                            }
                            else if(tadd2.matches("")){
                                Toast.makeText(SignupActivity.this,"Write Address line 2",Toast.LENGTH_SHORT).show();
                            }
                            else if(tpin.matches("")){
                                Toast.makeText(SignupActivity.this,"Write Pincode",Toast.LENGTH_SHORT).show();
                            }
                            else if(tpin.length() != 6){
                                Toast.makeText(SignupActivity.this,"Enter Proper Pincode",Toast.LENGTH_SHORT).show();
                            }
                            else if(tmobile.length() != 10){
                                Toast.makeText(SignupActivity.this,"Enter Proper Mobile no",Toast.LENGTH_SHORT).show();
                            }
                            else if(checkmobile(tmobile) == false)
                            {
                                Toast.makeText(SignupActivity.this,"Enter Proper Mobile No",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                sendData();
                            }


                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }

        });


    }

    public void sendData() {
        //String userEmail = MainActivity.MainCurrUserEmail;
        //String userKey = userEmail.replace(".","");
        //String userName = MainActivity.CurrUser;
        String userEmail = InputEmail.getText().toString();
        String userKey = InputEmail.getText().toString().replace(".","");
        String name = Fname.getText().toString();
        String mobile = Fmobile.getText().toString();
        String add1 = Fadd1.getText().toString();
        String add2 = Fadd2.getText().toString();
        String pin = Fpin.getText().toString();

        mRef = mData.getReference().child("users").child(userKey).child("address");
        mRef.setValue(add1);
        //Toast.makeText(SignupActivity.this,"Sending "+add1,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("address2");
        mRef.setValue(add2);
        //Toast.makeText(SignupActivity.this,"Sending "+add2,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("email");
        mRef.setValue(userEmail);
        //Toast.makeText(SignupActivity.this,"Sending "+userEmail,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("mobile");
        mRef.setValue(mobile);
        //Toast.makeText(SignupActivity.this,"Sending "+mobile,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("name");
        mRef.setValue(name);
        //Toast.makeText(SignupActivity.this,"Sending "+userName,Toast.LENGTH_SHORT).show();
        mRef = mData.getReference().child("users").child(userKey).child("pincode");
        mRef.setValue(pin);
        //Toast.makeText(SignupActivity.this,"Sending "+pin,Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean checkmobile(String mob){
        int flag = 0;
        for(int i=0;i<mob.length();i++){
            if(mob.charAt(i) == '0' || mob.charAt(i) == '1' || mob.charAt(i) == '2' || mob.charAt(i) == '3' || mob.charAt(i) == '4' || mob.charAt(i) == '5' || mob.charAt(i) == '6' || mob.charAt(i) == '7' || mob.charAt(i) == '8' || mob.charAt(i) == '9' ){
            }
            else{
                flag = 1;
                break;
            }
        }

        if(flag == 1){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}

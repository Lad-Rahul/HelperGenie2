package com.example.helpergenie2;

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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnReset;
    private EditText InputEmail;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        InputEmail = (EditText)findViewById(R.id.email_fp);
        btnReset = (Button)findViewById(R.id.btn_reset);
        progressBar = (ProgressBar)findViewById(R.id.progressBar_fp);

        auth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = InputEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(),"Enter your Registerd Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,"We have send you Instruction to reset Password on registerd Email",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this,"Failed to send Reset Email",Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}

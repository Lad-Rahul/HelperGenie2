package com.example.helpergenie2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterForm extends AppCompatActivity {

    private EditText Fmobile,Fadd1,Fadd2,Fpin;
    private TextView FnameText;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        this.setTitle("Registration Form");

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Please Fill This Form")
                .setMessage("In Order to Get your details you have to fill this details in given area")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); }
                });
        AlertDialog ad = builder.create();
        ad.show();


    }
}

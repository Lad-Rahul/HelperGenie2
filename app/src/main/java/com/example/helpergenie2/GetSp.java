package com.example.helpergenie2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class GetSp extends AppCompatActivity {

    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private String name,proffesion,email,mobile,SPpro;
    private int ordercomplete;
    private TextView textView;
    int count = 0;

    String recName[],recID[],recEmail[],recMobile[],recRating[],recProfession[];
    int recordercomplete[];
    ArrayList<String> rec2Name = new ArrayList<>();
    ArrayList<String> rec2ID = new ArrayList<>();
    ArrayList<String> rec2Email = new ArrayList<>();
    ArrayList<String> rec2Mobile = new ArrayList<>();
    ArrayList<String> rec2Rating = new ArrayList<>();
    ArrayList<String> rec2Profession = new ArrayList<>();
    ArrayList<Integer> rec2ordercomplete = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_get_sp);

        final ArrayList<String> ListSP2 = new ArrayList<>();
        ArrayList<String> ListSP = new ArrayList<>();
        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                ListSP = extras.getStringArrayList("Service Provider at this Location");
                SPpro = extras.getString("Service Provider Proffesion").toString();
                //Toast.makeText(get_sp.this,SPpro,Toast.LENGTH_SHORT).show();
            }
        }

        for(int i=0;i<ListSP.size();i++){
            //Toast.makeText(get_sp.this,ListSP.get(i).toString(),Toast.LENGTH_SHORT).show();
        }


        mData = FirebaseDatabase.getInstance();

        count = 0;

            for (int i = 0; i < ListSP.size(); i++) {
                final String tempSP = ListSP.get(i);
                mRef = mData.getReference().child("service-provider").child(tempSP);
                //Log.d("hello1", mData.getReference().child("service-provider").child(tempSP).child("name").toString());
                final int finalI = i;
                final ArrayList<String> finalListSP = ListSP;
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FireSP fireSP = dataSnapshot.getValue(FireSP.class);
                        name = fireSP.getName();
                        email = fireSP.getEmail();
                        mobile = fireSP.getMobile();
                        proffesion = fireSP.getProffesion();
                        ordercomplete = fireSP.getOrdercomplete();


                        if (proffesion.equals(SPpro)) {
                            Log.d("hello1", "name :" + name);
                            count++;
                            rec2Name.add(fireSP.getName());
                            rec2Email.add(fireSP.getEmail());
                            rec2Mobile.add(fireSP.getMobile());
                            rec2Rating.add(fireSP.getRating());
                            rec2ID.add(tempSP);
                            rec2ordercomplete.add(fireSP.getOrdercomplete());
                            rec2Profession.add(fireSP.getProffesion());
                            //ListSP2.add(tempSP);
                            //ListSP2.add(fireSP);
                        }

                        if (finalI == finalListSP.size() - 1) {

                            recName = new String[rec2Name.size()];
                            recMobile = new String[rec2Mobile.size()];
                            recEmail = new String[rec2Email.size()];
                            recRating = new String[rec2Email.size()];
                            recID = new String[rec2Name.size()];
                            recordercomplete = new int[rec2ordercomplete.size()];
                            recProfession = new String[rec2Profession.size()];

                            for (int j = 0; j < rec2Name.size(); j++) {
                                recName[j] = rec2Name.get(j);
                                recEmail[j] = rec2Email.get(j);
                                recMobile[j] = rec2Mobile.get(j);
                                recRating[j] = rec2Rating.get(j);
                                recID[j] = rec2ID.get(j);
                                recordercomplete[j] = rec2ordercomplete.get(j);
                                recProfession[j] = rec2Profession.get(j);
                            }


                                Intent go = new Intent(GetSp.this, DisplaySp.class);
                                go.putExtra("names", recName);
                                go.putExtra("emails", recEmail);
                                go.putExtra("mobiles", recMobile);
                                go.putExtra("ratings", recRating);
                                go.putExtra("IDs", recID);
                                go.putExtra("ordercomplete", recordercomplete);
                                go.putExtra("profession", recProfession);
                                startActivity(go);
                                finish();

                        }
                        //FireSP i = new FireSP(email,mobile,name,proffesion);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mRef.addValueEventListener(valueEventListener);
            }

    }
}


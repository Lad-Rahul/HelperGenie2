package com.example.helpergenie2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class History2Activity extends AppCompatActivity {

    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private FirebaseAuth auth;
    private LinearLayout mLinearLayout;
    LinearLayout l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);

        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();


       // View view = inflater.inflate(R.layout.fragment_history, container, false);
        //ImageView imageView = (ImageView) view.findViewById(R.id.my_image);
        mLinearLayout=(LinearLayout) findViewById(R.id.linearLayout_main);


        FirebaseUser currUser = auth.getCurrentUser();
        final String userEmail = currUser.getEmail().toString().replace(".","");
        mRef = mData.getReference().child("history").child(userEmail);



        mData.getReference().child("history").child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> id = new ArrayList<>();
                ArrayList<Map> records = new ArrayList<>();


                //Log.d("context",c1+"");
                long size=dataSnapshot.getChildrenCount();
                //String res=dataSnapshot.child(userEmail).getValue();

//
//                for(long i=0;i<size;i++){
//                    HistoryUser historyUser=dataSnapshot.getValue(HistoryUser.class);
//
//                }

                Log.d("aaaa",""+dataSnapshot.child(userEmail).getChildren());

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //Log.d("aaaa",ds.getKey());
                    HistoryUser historyUser=ds.getValue(HistoryUser.class);
                    //Log.d("aaaa",ds.getValue()+" ");
                    if(historyUser!=null)
                        Log.d("aaaa",historyUser.getName()+" "+historyUser.getProffession()+" "+historyUser.getTime());

                    //Log.d("context",getActivity()+"");

                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    l1=new LinearLayout(History2Activity.this);
                    l1.setLayoutParams(lparams);
                    l1.setOrientation(LinearLayout.VERTICAL);

                    TextView t1=new TextView(History2Activity.this);
                    t1.setLayoutParams(lparams);
                    t1.setTextSize(20);
                    if(historyUser!=null)
                        t1.setText(historyUser.getName());

                    l1.addView(t1);
                    //Log.d("aaaa",t1.getText()+"");
                    //Log.d("aaaa",mLinearLayout+"");
                    mLinearLayout.addView(l1);
                }

//                for(Map.Entry<String,Object> entry : ((Map<String,Object>)dataSnapshot.getValue()).entrySet()){
//                    String tempid = entry.getKey();
//                    Map map = (Map)entry.getValue();
//                    id.add(tempid);
//                    records.add(map);
//                }



                Log.d("history",records.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

package com.example.helpergenie2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.zip.Inflater;

public class HistoryActivity extends Fragment {

    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("History");

        auth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();


        FirebaseUser currUser = auth.getCurrentUser();
        String userEmail = currUser.getEmail().toString().replace(".","");
        mRef = mData.getReference().child("history").child(userEmail);

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> id = new ArrayList<>();
                ArrayList<Map> records = new ArrayList<>();

                for(Map.Entry<String,Object> entry : ((Map<String,Object>)dataSnapshot.getValue()).entrySet()){
                    String tempid = entry.getKey();
                    Map map = (Map)entry.getValue();
                    id.add(tempid);
                    records.add(map);

                }

                Log.d("history",records.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return inflater.inflate(R.layout.fragment_history, container, false);



    }


}

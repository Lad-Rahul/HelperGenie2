package com.example.helpergenie2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.zip.Inflater;

public class HistoryActivity extends Fragment {

    private FirebaseDatabase mData;
    private DatabaseReference mRef;
    private FirebaseAuth auth;
    private LinearLayout mLinearLayout;
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("History");

        startActivity(new Intent(getActivity(),History2Activity.class));

        mFragmentManager=getFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.alternatingLayout,new HomeActivity()).commit();

        return inflater.inflate(R.layout.fragment_history, container, false);

    }


}

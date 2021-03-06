package com.example.helpergenie2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends Fragment {

    View mView;
    Spinner searchSP;
    Spinner searchPin;
    private FirebaseDatabase mData;
    private DatabaseReference mRef,mRef2;
    private String selectPin,selectPro;
    private Button BtngetSP;
    ValueEventListener valList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_home,container,false);
        getActivity().setTitle("Home");

//        ConstraintLayout ctl = mView.findViewById(R.id.constraintLayout);
//        if(IsFirstTimeLaunced() == true){
//            ctl.setVisibility(ConstraintLayout.INVISIBLE);
//        }else{
//            ctl.setVisibility(ConstraintLayout.VISIBLE);
//        }
//        getActivity().setTitle("Home");

        Log.d("Pranav","EXE 1");
        String[] listSP = {"Plumber" , "Electrician" , "Carpenter","PestControl","HomeCleaner","Mechannic" };
        searchSP = (Spinner)mView.findViewById(R.id.search_sp);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,listSP);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSP.setAdapter(arrayAdapter);

        searchSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(),searchSP.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
                selectPro = searchSP.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Please wait");
        pd.setMessage("Fetching Available Pincodes");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();


        final ArrayList<String> pinObjectList = new ArrayList<>();
        searchPin = (Spinner)mView.findViewById(R.id.search_PIN);

        mData = FirebaseDatabase.getInstance();
        mRef = mData.getReference().child("pincode");
        //final ArrayList<String> pincodeList = new ArrayList<>();

        attachEventListner();
        //removeEventListner();

        searchPin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), searchPin.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                selectPin = searchPin.getItemAtPosition(i).toString();
                mRef2 = mData.getReference().child("pincode").child(selectPin);
                Log.d("Pranav","EXE 3");
                mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pinObjectList.clear();
                        for(Map.Entry<String,Object> entry : ((Map<String,Object>)dataSnapshot.getValue()).entrySet()){

                            Log.d("Pranav","EXE 4");

                            String SinglePin = (String)entry.getKey();
                            pinObjectList.add(SinglePin);
//                            Map SingleUser = (Map)entry.getValue();
//                            pinObjectList.add(SingleUser);

                        }

                        if(searchPin.getCount() == 0){
                            //getActivity().recreate();
                        }else{
                            pd.dismiss();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BtngetSP = (Button)mView.findViewById(R.id.btn_getSP);
        BtngetSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchPin.getSelectedItem() == null) {
                }else{
                    Intent intent = new Intent(getActivity(),GetSp.class);
                    Log.d("Pranav","EXE 5");
                    intent.putStringArrayListExtra("Service Provider at this Location",pinObjectList);
                    intent.putExtra("Service Provider Proffesion",selectPro);
                    startActivity(intent);
                }
            }
        });


        return mView;
    }

    private void collectPincode(Map<String,Object> users){

        ArrayList<Map> pinObjectList = new ArrayList<>();
        ArrayList<String> pinList = new ArrayList<>();

        for(Map.Entry<String,Object> entry : users.entrySet()){

            String SinglePin = (String)entry.getKey();
            Map SingleUser = (Map)entry.getValue();
            Log.d("aaa",SinglePin);
            pinList.add(SinglePin);
            pinObjectList.add(SingleUser);
        }

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,pinList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchPin.setAdapter(arrayAdapter2);

    }

    public void attachEventListner(){

        valList = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectPincode((Map<String,Object>) dataSnapshot.getValue());
                Log.d("Pranav","EXE 2");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addListenerForSingleValueEvent(valList);
    }

    public void removeEventListner(){
        mRef.removeEventListener(valList);
    }
}


package com.example.helpergenie2;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;

public class DisplaySp extends AppCompatActivity {

    RecyclerView rv;
    RecyclerSp ra;
    String rec3Name[],rec3ID[],rec3Email[],rec3Mobile[],rec3Rating[],rec3Profession[];
    int rec3ordercomplete[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sp);

        Bundle extra=getIntent().getExtras();
        rec3ID = extra.getStringArray("IDs");
        rec3Mobile = extra.getStringArray("mobiles");
        rec3Email = extra.getStringArray("emails");
        rec3Name = extra.getStringArray("names");
        rec3Rating = extra.getStringArray("ratings");
        rec3ordercomplete = extra.getIntArray("ordercomplete");
        rec3Profession = extra.getStringArray("profession");

        if(rec3ID.length == 0){
            ConstraintLayout cl = (ConstraintLayout)findViewById(R.id.c_l);
            TextView tv = new TextView(this);
            tv.setText("No service availble");
            tv.setLayoutParams(new ConstraintLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv.setTextSize(24);
            cl.addView(tv);
        }
        else {

            rv = (RecyclerView) findViewById(R.id.recyclerViewSP);

            ra = new RecyclerSp(this, rec3Name, rec3Email, rec3Mobile, rec3ID, rec3Rating, rec3ordercomplete, rec3Profession);
            rv.setAdapter(ra);
            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(this));

        }
    }

}

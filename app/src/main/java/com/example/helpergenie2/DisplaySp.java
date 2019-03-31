package com.example.helpergenie2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DisplaySp extends AppCompatActivity {

    RecyclerView rv;
    RecyclerSp ra;
    String rec3Name[],rec3ID[],rec3Email[],rec3Mobile[],rec3Rating[];
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


        rv = (RecyclerView) findViewById(R.id.recyclerViewSP);

        ra = new RecyclerSp(this,rec3Name,rec3Email,rec3Mobile,rec3ID,rec3Rating,rec3ordercomplete);
        rv.setAdapter(ra);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }
}

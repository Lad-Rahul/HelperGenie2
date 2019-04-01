package com.example.helpergenie2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerSp extends RecyclerView.Adapter<RecyclerSp.HolderClass> {
    Context ct;
    String name[],mobile[],email[],id[],rating[],profession[];
    int ordercomplete[];

    private FirebaseAuth auth;
    private FirebaseDatabase mData;
    private DatabaseReference mRef;


//    private final List<Button> buttonList;
//
//    public RecyclerSp(List<Button> button, ClickListener listener) {
//        this.listener = listener;
//        this.buttonList = button;
//    }



    public RecyclerSp(Context ctx,String dataname[],String dataemail[],String datamobile[],String dataid[],String datarating[],int dataordercomplete[],String dataProfession[]) {
        ct = ctx;
        name = dataname;
        mobile = datamobile;
        email = dataemail;
        id = dataid;
        rating = datarating;
        ordercomplete = dataordercomplete;
        profession = dataProfession;
    }

    @Override
    public HolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflator = LayoutInflater.from(ct);
        View mView = mInflator.inflate(R.layout.sp_details,parent,false);
        return new HolderClass(mView);
    }

    @Override
    public void onBindViewHolder(HolderClass holder, final int position) {
        final HolderClass finalHolder = holder;
        holder.name.setText(name[position]);
        holder.emailID.setText(email[position]);
        holder.mobile.setText(mobile[position]);
        holder.ordercomplete.setText(ordercomplete[position]+"");
        float ratingVal = Float.valueOf(rating[position]);
        holder.ratingBar.setRating(ratingVal);
        holder.ratingBar.setIsIndicator(true);
        holder.phoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + finalHolder.mobile.getText().toString()));
                ct.startActivity(intent);
            }
        });
        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = id[position];
                //Toast.makeText(ct,text,Toast.LENGTH_LONG).show();
                Integer newOrder = ordercomplete[position]+1;

                auth = FirebaseAuth.getInstance();
                mData = FirebaseDatabase.getInstance();
                mRef = mData.getReference().child("service-provider").child(text).child("ordercomplete");
                mRef.setValue(newOrder);

                FirebaseUser currUser = auth.getCurrentUser();
                String userEmail = currUser.getEmail().toString().replace(".","");
                mRef = mData.getReference().child("history").child(userEmail);
                Calendar currentTime;
                currentTime = Calendar.getInstance();



                String monthName[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
                String currTime = currentTime.get(Calendar.DAY_OF_MONTH) +"-" + monthName[currentTime.get(Calendar.MONTH)+1] + "-" + currentTime.get(Calendar.YEAR) + " " + currentTime.get(Calendar.HOUR) + ":" + currentTime.get(Calendar.MINUTE);

                Map<String,Object> map = new HashMap<String, Object>();
                map.put("time",currTime);
                map.put("name",name[position]);
                map.put("proffession",profession[position]);

                Log.d("time",currTime);

                mRef.push().setValue(map);
                ((Activity)ct).finish();
            }

        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class HolderClass extends RecyclerView.ViewHolder {

        TextView name,emailID,mobile,ordercomplete,id;
        Button btnOrder;
        ImageView phoneCall;
        RatingBar ratingBar;

        public HolderClass(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.spName);
            emailID = (TextView) itemView.findViewById(R.id.spEmail);
            mobile = (TextView) itemView.findViewById(R.id.spMobile);
            ordercomplete = (TextView) itemView.findViewById(R.id.spordercomplete);
            phoneCall = (ImageView) itemView.findViewById(R.id.phoneCall);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            btnOrder = (Button)itemView.findViewById(R.id.btn_order);
        }




//        MyAdapter adapter = new MyAdapter(myItems, new ClickListener() {
//            @Override public void onPositionClicked(int position) {
//                // callback performed on click
//            }
//
//            @Override public void onLongClicked(int position) {
//                // callback performed on click
//            }
//        });

    }


}

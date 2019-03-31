package com.example.helpergenie2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecyclerSp extends RecyclerView.Adapter<RecyclerSp.HolderClass> {
    Context ct;
    String name[],mobile[],email[],id[],rating[];
    int ordercomplete[];

       ClickListener listener = null;
//    private final List<Button> buttonList;
//
//    public RecyclerSp(List<Button> button, ClickListener listener) {
//        this.listener = listener;
//        this.buttonList = button;
//    }



    public RecyclerSp(Context ctx,String dataname[],String dataemail[],String datamobile[],String dataid[],String datarating[],int dataordercomplete[]) {
        ct = ctx;
        name = dataname;
        mobile = datamobile;
        email = dataemail;
        id = dataid;
        rating = datarating;
        ordercomplete = dataordercomplete;
    }

    @Override
    public HolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflator = LayoutInflater.from(ct);
        View mView = mInflator.inflate(R.layout.sp_details,parent,false);
        return new HolderClass((mView),listener);
    }

    @Override
    public void onBindViewHolder(HolderClass holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class HolderClass extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,emailID,mobile,ordercomplete;
        Button btnOrder;
        ImageView phoneCall;
        RatingBar ratingBar;
        private WeakReference<ClickListener> listenerRef;

        public HolderClass(View itemView,ClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.spName);
            emailID = (TextView) itemView.findViewById(R.id.spEmail);
            mobile = (TextView) itemView.findViewById(R.id.spMobile);
            ordercomplete = (TextView) itemView.findViewById(R.id.spordercomplete);
            phoneCall = (ImageView) itemView.findViewById(R.id.phoneCall);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            btnOrder = (Button)itemView.findViewById(R.id.btn_order);

            listenerRef = new WeakReference<>(listener);
            itemView.setOnClickListener(this);
            btnOrder.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == btnOrder.getId()){
                String text = "btn : " + btnOrder.getId();

                Toast.makeText(ct,text, Toast.LENGTH_SHORT).show();
            }
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

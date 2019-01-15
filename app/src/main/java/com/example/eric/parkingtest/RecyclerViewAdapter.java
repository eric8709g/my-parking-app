package com.example.eric.parkingtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Parking> mData ;

    public RecyclerViewAdapter(Context mContext, List<Parking> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.text_pArea.setText(mData.get(position).getpArea());
        holder.text_pName.setText(mData.get(position).getpName());
        holder.text_pServicetime.setText(mData.get(position).getpServiceTime());
        holder.text_pAddress.setText(mData.get(position).getpAddress());
        holder.pdTW97X = mData.get(position).getpTW97X();
        holder.pdTW97Y = mData.get(position).getpTW97Y();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ParkingDetail_Activity.class);
                intent.putExtra("name",mData.get(position).getpName());
                intent.putExtra("area",mData.get(position).getpArea());
                intent.putExtra("pServicetime",mData.get(position).getpServiceTime());
                intent.putExtra("address",mData.get(position).getpAddress());
                intent.putExtra("tw97x" , mData.get(position).getpTW97X());
                intent.putExtra("tw97y" , mData.get(position).getpTW97Y());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_pArea;
        TextView text_pName;
        TextView text_pServicetime;
        TextView text_pAddress;
        double pdTW97X , pdTW97Y;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            text_pArea = (TextView) itemView.findViewById(R.id.textView_area);
            text_pName = (TextView) itemView.findViewById(R.id.textView_name);
            text_pServicetime = (TextView) itemView.findViewById(R.id.textView_servicetime);
            text_pAddress = (TextView) itemView.findViewById(R.id.textView_address);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}

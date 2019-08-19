package com.example.nppproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nppproject.Entity.ContentEntity;
import com.example.nppproject.R;

import java.util.ArrayList;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    ArrayList<ContentEntity> mlistContent;
    Context mContext;

    public ContentAdapter(ArrayList<ContentEntity> mlistContent) {
        this.mlistContent = mlistContent;
    }



    @NonNull
    @Override
    public ContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_detail, parent, false);
        mContext = parent.getContext();

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentAdapter.MyViewHolder holder, int position) {

        try {
            ContentEntity contentEntity = mlistContent.get(position);
//            String title = contentEntity.getTittle();
//            String time = contentEntity.getPubDate();

            ArrayList<String> content=contentEntity.getContent();
     //       String shortTitle = contentEntity.getShortTittle();
       //     String urlImg = contentEntity.getUrlImg();
//            holder.tvTitle.setText(title);
////            holder.tvShortTitle.setText(shortTitle);
////            holder.tvTime.setText(time);
////            Log.d("alt",shortTitle);
//            if (){holder.tvContent.setText();}
//            else{
//            Glide.with(mContext).load(content).into(holder.imgContent);}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView tvContent, tvTitle, tvShortTitle, tvTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvTitle=itemView.findViewById(R.id.tvTitle);
//            tvShortTitle=itemView.findViewById(R.id.tvShortTitle);
//            tvTime=itemView.findViewById(R.id.tvTime);
            tvContent=itemView.findViewById(R.id.tvContent);
            imgContent=itemView.findViewById(R.id.imgContent);

        }
    }
}

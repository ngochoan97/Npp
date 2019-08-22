package com.example.nppproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;
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
import java.util.regex.Pattern;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    ArrayList<String> mListContent;
    ContentEntity contentEntity;
    Context mContext;

    String REGEX_SPLIT_IMG_URL = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpeg|jpg|gif|png)";

//    public ContentAdapter(ArrayList<ContentEntity> mListContent) {
//        this.mListContent = mListContent;
//    }

    public ContentAdapter(ArrayList<String> content) {
        this.mListContent = content;
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
            //        ContentEntity contentEntity = mListContent.get(position);

//             mListContent=contentEntity.getContent();

            //String urlImg = contentEntity.getUrlImg();

            String content = mListContent.get(position);
            if (mListContent.get(position) == REGEX_SPLIT_IMG_URL) {

                Glide.with(mContext).load(content).into(holder.imgContent);
            } else {
                holder.tvContent.setText(content);
            }


//            if (listContent){Glide.with(mContext).load(content).into(holder.imgContent);}
//            else{
//                holder.tvContent.setText(content);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListContent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView tvContent, tvTitle, tvShortTitle, tvTime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvTitle=itemView.findViewById(R.id.tvTitle);
//            tvShortTitle=itemView.findViewById(R.id.tvShortTitle);
//            tvTime=itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            imgContent = itemView.findViewById(R.id.imgContent);

        }
    }

}

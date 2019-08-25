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
import com.example.nppproject.Entity.PostEntity;
import com.example.nppproject.R;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    ArrayList<String> mListContent;
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





        //        ContentEntity contentEntity = mListContent.get(position);

//             mListContent=contentEntity.getContent();

        //String urlImg = contentEntity.getUrlImg();
        Pattern pattern;
        pattern=Pattern.compile(REGEX_SPLIT_IMG_URL);
        String content = mListContent.get(position);
       if (pattern.matcher(content).matches()) {
             Glide.with(mContext).load(content).into(holder.imgContent);
        } else {
            holder.tvContent.setText(content);
        }

//            if (listContent){Glide.with(mContext).load(content).into(holder.imgContent);}
//            else{
//                holder.tvContent.setText(content);
//            }

    }



    @Override
    public int getItemCount() {
        return mListContent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView tvContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            imgContent = itemView.findViewById(R.id.imgContent);
        }
    }

}

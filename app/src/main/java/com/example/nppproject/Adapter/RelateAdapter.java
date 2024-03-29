package com.example.nppproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nppproject.Entity.PostEntity;
import com.example.nppproject.R;
import com.example.nppproject.interfaces.ClickListener;

import java.util.ArrayList;

public class RelateAdapter extends RecyclerView.Adapter<RelateAdapter.MyViewHolder> {
    ArrayList<PostEntity> mListPost;
    Context mContext;
    static ClickListener mClickListener;

    public RelateAdapter(ArrayList<PostEntity> mListPost) {

        this.mListPost = mListPost;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            PostEntity postEntity = mListPost.get(position);

            String title = postEntity.getTitle();
            String urlImg = postEntity.getUrlImg();
            String time = postEntity.getTime();
            holder.tvTitle.setText(title);
            holder.tvTime.setText(time);
            Glide.with(mContext).load(urlImg).into(holder.imgPost);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListPost.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPost, imgTop;
        TextView tvTitle, tvTime, tvTimeTop, tvTitleTop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgPost = itemView.findViewById(R.id.imgPost);

        }


        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setOnClickListener(ClickListener clickListener) {
        RelateAdapter.mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }

}

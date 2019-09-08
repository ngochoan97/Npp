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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    ArrayList<PostEntity> mListPost;

    Context mContext;
    static ClickListener mClickListener;


    public PostAdapter(ArrayList<PostEntity> mListPost) {
        this.mListPost = mListPost;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_item, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Glide.with(mContext).load(R.mipmap.crop).into(holder.imgNetwork);
        try {
            PostEntity postEntity = mListPost.get(position);
            if (position == 0) {
                holder.tvTitleTop.setText(postEntity.getTitle());
                holder.tvTimeTop.setText(postEntity.getTime());
                Glide.with(mContext).load(postEntity.getUrlImg()).placeholder(R.drawable.ic_menu_share).into(holder.imgTop);
            } else {
                String title = postEntity.getTitle();
                String urlImg = postEntity.getUrlImg();
                String time = postEntity.getTime();
                holder.tvTitle.setText(title);
                holder.tvTime.setText(time);
                Glide.with(mContext).load(urlImg).placeholder(R.drawable.ic_menu_share).into(holder.imgPost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mListPost.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPost,imgTop,imgNetwork;
        TextView tvTitle, tvTime,tvTimeTop,tvTitleTop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imgPost = itemView.findViewById(R.id.imgPost);
            imgTop = itemView.findViewById(R.id.imgTop);
            imgNetwork = itemView.findViewById(R.id.imgNetwork);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTimeTop = itemView.findViewById(R.id.tvTimeTop);
            tvTitleTop = itemView.findViewById(R.id.tvTitleTop);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PostAdapter.mClickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}

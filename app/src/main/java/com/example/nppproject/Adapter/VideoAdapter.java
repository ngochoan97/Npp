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
import com.example.nppproject.Entity.VideoEntity;
import com.example.nppproject.R;
import com.example.nppproject.interfaces.ClickListener;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {
    private ArrayList<VideoEntity> mListVideo;
    Context mContext;
ClickListener clickListener;


    public VideoAdapter(ArrayList<VideoEntity> mListVideo) {
        this.mListVideo = mListVideo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_video, parent, false);

        mContext = parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTitleVD.setText(mListVideo.get(position).getTitle());
        Glide.with(mContext).load(mListVideo.get(position).getAvatar()).placeholder(R.mipmap.play_button).into(holder.imgVideo);
    }

    @Override
    public int getItemCount() {
        return mListVideo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgVideo;
        TextView tvTitleVD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVideo = itemView.findViewById(R.id.imgVideo);
            tvTitleVD = itemView.findViewById(R.id.tvTitleVD);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view, false);
        }
    }
    public void setOnClickItemListener(ClickListener clickListener) {
       this.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v, boolean check);
    }
}

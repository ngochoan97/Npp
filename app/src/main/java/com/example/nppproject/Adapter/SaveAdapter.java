package com.example.nppproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nppproject.Entity.ContentSaveEntity;
import com.example.nppproject.R;
import com.example.nppproject.interfaces.ClickListener;

import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.ViewHorder> {
    Context context;
    List<ContentSaveEntity> contentSaveEntityList;
    LayoutInflater inflater;
    static ClickListener mClickListener;
    public SaveAdapter(Context context, List<ContentSaveEntity> contentSaveEntityList) {
        this.context = context;
        this.contentSaveEntityList = contentSaveEntityList;
        inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHorder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.item_save, parent, false);
        return new ViewHorder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHorder holder, int position) {
        holder.tvTitle.setText(contentSaveEntityList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return contentSaveEntityList.size();
    }

    public class ViewHorder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        public ViewHorder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitleItemSave);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public void setOnClickListener(ClickListener clickListener) {
        SaveAdapter.mClickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}

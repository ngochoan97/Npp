package com.example.nppproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.example.nppproject.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
    Context mContext;


    public SliderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_toolbar, null);
        return new SliderAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        switch (position) {
            case 0:{
                Glide.with(mContext).load(R.mipmap.banner1).placeholder(R.drawable.ic_menu_gallery).error(R.drawable.ic_menu_gallery).into(viewHolder.imageToolbar);
                break;
            } case 1:{
                Glide.with(mContext).load(R.mipmap.banner2)
                        .placeholder(R.drawable.ic_menu_gallery).error(R.drawable.ic_menu_gallery).into(viewHolder.imageToolbar);
                break;
            } case 2:{
                Glide.with(mContext).load(R.mipmap.banner3)
                        .placeholder(R.drawable.ic_menu_gallery).error(R.drawable.ic_menu_gallery).into(viewHolder.imageToolbar);
                break;
            }

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        ImageView imageToolbar;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageToolbar = itemView.findViewById(R.id.imageBackgourdToolbar);
        }
    }
}

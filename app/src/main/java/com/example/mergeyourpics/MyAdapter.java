package com.example.mergeyourpics;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<String> allImageList;

    public MyAdapter(Context mContext, ArrayList<String> allImageList) {
        this.mContext = mContext;
        this.allImageList = allImageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,
                    parent, false);
            return new ImagesViewHolder(view);
        }
        else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_button_layout,
                    parent, false);
            return new SelectImageFromMemoryViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position != 0)
        {
            ImagesViewHolder imagesViewHolder = (ImagesViewHolder) holder;

            Glide.with(mContext).load(allImageList.get(position - 1)).into(((ImagesViewHolder) holder).mImage);
            imagesViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("click", "clicked");
                }
            });
           // imagesViewHolder.mImageSelectedOrNot
        }
        else{
            SelectImageFromMemoryViewHolder imageFromMemoryViewHolder = (SelectImageFromMemoryViewHolder) holder;
            imageFromMemoryViewHolder.button.setImageResource(R.drawable.ic_launcher_background);

            imageFromMemoryViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("click", "clicked button");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(allImageList == null) return 0;

        return allImageList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        return position == 0 ? 1 : 0;
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public ImageView mImageSelectedOrNot;
        public ImagesViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivPlace);
            mImageSelectedOrNot = itemView.findViewById(R.id.image_selected_or_not);
        }
    }

    class SelectImageFromMemoryViewHolder extends RecyclerView.ViewHolder {
        public ImageButton button;
        public SelectImageFromMemoryViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.choose_img_form_memory_btn);
        }
    }
}

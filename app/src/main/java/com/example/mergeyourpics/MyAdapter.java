package com.example.mergeyourpics;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,
                parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;

        Glide.with(mContext).load(allImageList.get(position)).into(((PlaceViewHolder) holder).mImage);
        placeViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click", "clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(allImageList == null) return 0;

        return allImageList.size();
    }


    class PlaceViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public PlaceViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivPlace);
        }
    }
}

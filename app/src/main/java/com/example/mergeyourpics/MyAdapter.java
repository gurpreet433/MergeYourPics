package com.example.mergeyourpics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private int[] mPlaceList;

    public MyAdapter(Context mContext, int[] mPlaceList) {
        this.mContext = mContext;
        this.mPlaceList = mPlaceList;
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
        placeViewHolder.mPlace.setImageResource(mPlaceList[position]);
    }

    @Override
    public int getItemCount() {
        return mPlaceList.length;
    }


    class PlaceViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPlace;
        public PlaceViewHolder(View itemView) {
            super(itemView);
            mPlace = itemView.findViewById(R.id.ivPlace);
        }
    }
}

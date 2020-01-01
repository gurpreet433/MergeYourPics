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

public class MyAdapter extends SelectableAdapter<MyAdapter.ImagesViewHolder> {

    private Context mContext;
    private ArrayList<String> allImageList;
    private ImagesViewHolder.ClickListener clickListener;

    public MyAdapter(Context mContext, ArrayList<String> allImageList, ImagesViewHolder.ClickListener clickListener) {
        this.mContext = mContext;
        this.allImageList = allImageList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyAdapter.ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,
                    parent, false);
            return new ImagesViewHolder(view, clickListener);
        }
        else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_button_layout,
                    parent, false);
            return new SelectImageFromMemoryViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        if(position != 0)
        {
            ImagesViewHolder imagesViewHolder = (ImagesViewHolder) holder;

            Glide.with(mContext).load(allImageList.get(position - 1)).into(((ImagesViewHolder) holder).mImage);
//            imagesViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.i("click", "clicked");
//                }
//            });

            holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

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

    public static class ImagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener  {
        public ImageView mImage;
        public ImageView mImageSelectedOrNot;
        private ClickListener listener;
        private final View selectedOverlay;

        public ImagesViewHolder(View itemView, ClickListener listener) {
            super(itemView);
            mImage = itemView.findViewById(R.id.ivPlace);
            mImageSelectedOrNot = itemView.findViewById(R.id.image_not_selected);
            this.listener = listener;

            selectedOverlay = (View) itemView.findViewById(R.id.image_selected);//todo
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener (this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                Log.i("clicked", "clicked");
                listener.onItemClicked(getAdapterPosition ());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (listener != null) {
                Log.i("clicked", "Longclicked");
                return listener.onItemLongClicked(getAdapterPosition ());
            }
            return false;
        }
        public interface ClickListener {
           void onItemClicked(int position);

           boolean onItemLongClicked(int position);
        }

    }


    class SelectImageFromMemoryViewHolder extends MyAdapter.ImagesViewHolder {
        public ImageButton button;
        public SelectImageFromMemoryViewHolder(@NonNull View itemView) {
            super(itemView, clickListener);
            button = itemView.findViewById(R.id.choose_img_form_memory_btn);
        }
    }
}

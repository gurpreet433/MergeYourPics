package com.example.mergeyourpics;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

    public class ItemDecorationRecyclerView extends RecyclerView.ItemDecoration {

        private final int mSpaceHeight;

        public ItemDecorationRecyclerView(int mSpaceHeight) {
            this.mSpaceHeight = mSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {

            int position = parent.getChildAdapterPosition(view); // item position
            outRect.top = mSpaceHeight;
            if ((position + 1) % 3 == 0)
            {
                outRect.right = mSpaceHeight;

            }
            else if(position % 3 == 0)
            {
                outRect.left = mSpaceHeight;
            }
            else
            {
                outRect.left = mSpaceHeight;
                outRect.right = mSpaceHeight;
            }

        }

}

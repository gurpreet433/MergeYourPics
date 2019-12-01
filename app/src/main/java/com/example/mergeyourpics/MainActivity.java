package com.example.mergeyourpics;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    Button showMoreButton;
    boolean isShowingMenu;
    LinearLayout linearLayout;
    LinearLayout menuLinearLayout;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        removeShadowOfActionBar();
        showMoreButton = findViewById(R.id.shore_more_button);

        // initializing the view
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.menu_layout, null);
        view.setVisibility(View.GONE);
        linearLayout = findViewById(R.id.linear_layout);
        linearLayout.addView(view);

        isShowingMenu = false;
        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowingMenu) {
                    showMoreButton.setText(R.string.down_arrow);
                    slideUpView(view);
                    isShowingMenu = false;
                }
                else{
                    showMoreButton.setText(R.string.up_arrow);
                    slideDownView(view);
                    isShowingMenu = true;
                }
            }
        });


        int[] mPlaceList = new int[]{R.drawable.ic_launcher_background, R.drawable.shape_circle, R.drawable.ic_launcher_background};

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView.setLayoutManager(mGridLayoutManager);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, mPlaceList);
        recyclerView.setAdapter(myAdapter);

    }

    private void slideDownView(final View v)
    {
        v.setVisibility(View.VISIBLE);
        Log.i("height", v.getHeight()+ "a" + v.getMeasuredHeight());
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 350);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                v.clearAnimation();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void slideUpView(final View v) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, 0);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                v.clearAnimation();
            }
        });
    }

    private void removeShadowOfActionBar() {
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.about_menu_item)
        {
            Log.d("here", "here we go");
        }
        return true;
    }
}

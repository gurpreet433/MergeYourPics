package com.example.mergeyourpics;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        linearLayout = findViewById(R.id.linear_layout);

        isShowingMenu = false;
        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowingMenu) {
                    showMoreButton.setText(R.string.down_arrow);
                    linearLayout.removeView(view);
                    isShowingMenu = false;
                }
                else{
                    showMoreButton.setText(R.string.up_arrow);
                    linearLayout.addView(view);
                    isShowingMenu = true;
                }
            }
        });

        int[] mPlaceList = new int[]{R.drawable.ic_launcher_background, R.drawable.shape_circle};

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, mPlaceList);
        recyclerView.setAdapter(myAdapter);

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

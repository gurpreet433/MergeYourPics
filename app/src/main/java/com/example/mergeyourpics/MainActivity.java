package com.example.mergeyourpics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    Button showMoreButton;
    boolean isShowingMenu;
    LinearLayout linearLayout;
    LinearLayout menuLinearLayout;

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

        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowingMenu) {
                    showMoreButton.setText(R.string.up_arrow);
                    linearLayout.removeView(findViewById(R.id.new_menu_layout));
                    isShowingMenu = false;
                }
                else{
                    showMoreButton.setText(R.string.down_arrow);
                    linearLayout.addView(view);
                    isShowingMenu = true;
                }
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

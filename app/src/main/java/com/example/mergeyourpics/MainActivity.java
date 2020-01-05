package com.example.mergeyourpics;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.colorpickerview.ActionMode;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.listeners.ColorListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements MyAdapter.ImagesViewHolder.ClickListener   {

    Button showMoreButton;
    boolean isShowingMenu;
    LinearLayout linearLayout;
    LinearLayout menuLinearLayout;
    RecyclerView recyclerView;
    ArrayList<String> allImagesPath;
    MyAdapter myAdapter;
    PhotoSettings photoSettings;

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

        photoSettings = new PhotoSettings();
        setSettingsButtonsClickEvents(view);

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

        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
            allImagesPath = getAllShownImagesPath(this);
            setupRecyclerView();
        }
    }

    private void setSettingsButtonsClickEvents(final View view) {
        final LinearLayout StackVerticallyLayout = view.findViewById(R.id.stack_vertically_layout);
        final LinearLayout BackgroundFillLayout  = view.findViewById(R.id.background_fill_layout);
        final LinearLayout ImageSpacingLayout  = view.findViewById(R.id.image_spacing_layout);
        final LinearLayout ScaleLargerLayout  = view.findViewById(R.id.scale_larger_layout);

        StackVerticallyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "click1");
                ImageButton stackButton = view.findViewById(R.id.stack_button);
                TextView stackText = view.findViewById(R.id.stack_text);

                if (photoSettings.getStackVertically() == true)
                {
                    stackButton.setBackgroundResource(R.drawable.tick_icon_ticked);
                    stackText.setText("Stack horizontally");
                }else
                {
                    stackButton.setBackgroundResource(R.drawable.tick_icon);
                    stackText.setText("Stack vertically");
                }
                photoSettings.toggleStackVertically();
            }
        });

        BackgroundFillLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ColorPickerDialog.Builder(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("ColorPicker Dialog")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton("select color",
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        photoSettings.setColorChoice(envelope.getColor());
                                    }
                                }).
                        setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .attachAlphaSlideBar(true)
                        .attachBrightnessSlideBar(true)
                        .show();

            }
        });

        ImageSpacingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createLongDialog(MainActivity.this);
            }
        });

        ScaleLargerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "click2");
                ImageButton stackButton = view.findViewById(R.id.scale_image_button);
                TextView stackText = view.findViewById(R.id.scale_image_textView);

                if (photoSettings.getScaleImageToSmallest() == true)
                {
                    stackButton.setBackgroundResource(R.drawable.tick_icon_ticked);
                    stackText.setText("Scale smaller images to largest");
                }else
                {
                    stackButton.setBackgroundResource(R.drawable.tick_icon);
                    stackText.setText("Scale larger images to smallest");
                }
                photoSettings.toggleScaleImageToSmallest();
            }
        });
    }

    private void createLongDialog(Context context) {
        Log.i("here", "hereere");
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.image_spacing_layout);

        final SeekBar horizontalSeekBar = dialog.findViewById(R.id.horizontal_seek_bar);
        final SeekBar verticalSeekBar = dialog.findViewById(R.id.vertical_seek_bar);
        final TextView horizontalDistanceText = dialog.findViewById(R.id.horizontal_value);
        final TextView verticalDistanceText = dialog.findViewById(R.id.vertical_value_text);

        horizontalSeekBar.setProgress(photoSettings.getHorizontalSpacing());
        horizontalDistanceText.setText(photoSettings.getHorizontalSpacing() + "");
        verticalSeekBar.setProgress(photoSettings.getVerticalSpacking());
        verticalDistanceText.setText(photoSettings.getVerticalSpacking()+ "");

        Button cancelButton = dialog.findViewById(R.id.cancle_button_dialog);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button okButton = dialog.findViewById(R.id.ok_button_dialog);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoSettings.setHorizontalSpacing(horizontalSeekBar.getProgress());
                photoSettings.setVerticalSpacking(verticalSeekBar.getProgress());
                dialog.dismiss();
            }
        });

        horizontalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                horizontalDistanceText.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        verticalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               verticalDistanceText.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        dialog.show();
    }

    void setupRecyclerView(){
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView.setLayoutManager(mGridLayoutManager);

        myAdapter = new MyAdapter(MainActivity.this, allImagesPath, this);
        recyclerView.setAdapter(myAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new ItemDecorationRecyclerView(5);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }



    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }


    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    allImagesPath = getAllShownImagesPath(this);
                    setupRecyclerView();
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public static ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(0 ,absolutePathOfImage);
        }
        return listOfAllImages;
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

    @Override
    public void onItemClicked(int position) {
        toggleSelection(position);
        updateButton();
    }

    @Override
    public boolean onItemLongClicked(int position) {
        toggleSelection(position);
        updateButton();
        return true;
    }


    // Pick images from memory
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    ArrayList<String> absolutePathList;
    @Override
    public void onChooseImagesClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                absolutePathList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String absolutePathOfImage = cursor.getString(columnIndex);
                    absolutePathList.add(absolutePathOfImage);
                    Log.i("column", columnIndex+":");////
                    imageEncoded  = cursor.getString(columnIndex);

                    for (int i = 0; i < absolutePathList.size(); i++)
                    {
                        if (allImagesPath.contains(absolutePathList.get(i))){
                            Log.v("LOG_TAG", "\nlala" + absolutePathList.get(i));
                            toggleSelection(allImagesPath.indexOf(absolutePathList.get(i)) + 1);
                        }
                    }
                    updateButton();
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn,
                                    null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            Log.i("column", columnIndex+":");////
                            imageEncoded  = cursor.getString(columnIndex);
                            String absolutePathOfImage = cursor.getString(columnIndex);
                            absolutePathList.add(absolutePathOfImage);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }

                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.get(0) + "\n" + mArrayUri.get(1));

                        for (int i = 0; i < absolutePathList.size(); i++)
                        {
                            if (allImagesPath.contains(absolutePathList.get(i))){
                                Log.v("LOG_TAG", "\nlala" + absolutePathList.get(i));
                                toggleSelection(allImagesPath.indexOf(absolutePathList.get(i)) + 1);
                             }
                        }
                        updateButton();

                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void toggleSelection(int position) {
        myAdapter.toggleSelection (position);

    }

    private void updateButton()
    {
        Button mergeButton = findViewById(R.id.merge_images_button);
        int count = myAdapter.getSelectedItemCount();
        if(count == 0)
        {
            mergeButton.setText("MERGE");
            mergeButton.setEnabled(false);
            mergeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        else{
            mergeButton.setEnabled(true);
            mergeButton.setText("MERGE" + " (" + count + ") ");
            mergeButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }
}

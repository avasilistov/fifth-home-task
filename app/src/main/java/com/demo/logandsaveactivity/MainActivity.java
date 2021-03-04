package com.demo.logandsaveactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.demo.logandsaveactivity.Utils.writeCall;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private FloatingActionButton shareBtn;
    private FloatingActionButton addBtn;
    private FloatingActionButton cameraBtn;
    private FloatingActionButton imageBtn;
    private Boolean clicked = false;
    private Animation rotateOpen ;
    private Animation rotateClose ;
    private Animation fromBottom;
    private Animation toBottom;
    private ImageView imgContainer;
    private String currentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.add_btn);
        shareBtn = findViewById(R.id.share_btn);
        cameraBtn = findViewById(R.id.add_photo_btn);
        imageBtn = findViewById(R.id.pick_image_btn);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        imgContainer = findViewById(R.id.image_view_container);

//       Glide.with(this).load("http://goo.gl/gEgYUd").fitCenter().into(imgContainer);

//        Log.i("zzz", MediaStore.Images.Media.getContentUri("images"));
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        imageBtn.setOnClickListener(v -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
                });

// Share log file

//        shareButton = findViewById(R.id.share_button);
//        shareButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File filePath = new File(getBaseContext().getFilesDir(), "");
//                File newFile = new File(filePath, "Log.txt");
//                Uri contentUri = getUriForFile(getBaseContext(), "com.demo.logandsaveactivity", newFile);
//                Intent shareFileIntent = new Intent();
//                shareFileIntent.setAction(Intent.ACTION_SEND);
//                shareFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                shareFileIntent.setType("text/plain");
//                shareFileIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
//                startActivity(Intent.createChooser(shareFileIntent, "Share"));
//            }
//        });


        writeCall("onCreate", this);

    }

    protected void onActivityResult(int requstCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requstCode, resultCode, data);
        Log.i("zzz", "resutCode = "+ resultCode);

        if (resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null){
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                Log.i("zzz", "null");
                if (cursor!= null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Log.i("zzz", picturePath);
//                    imgContainer.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    Glide.with(this).load(picturePath).fitCenter().into(imgContainer);
                    cursor.close();
                }
            }
        }

    }


    private void onAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setAnimation(Boolean clicked) {
        if (!clicked){
            addBtn.startAnimation(rotateOpen);
            shareBtn.startAnimation(fromBottom);
            imageBtn.startAnimation(fromBottom);
            cameraBtn.startAnimation(fromBottom);
        } else {
            addBtn.startAnimation(rotateClose);
            shareBtn.startAnimation(toBottom);
            imageBtn.startAnimation(toBottom);
            cameraBtn.startAnimation(toBottom);
        }

    }

    private void setVisibility(Boolean clicked) {
        if (!clicked){
            shareBtn.setVisibility(View.VISIBLE);
            imageBtn.setVisibility(View.VISIBLE);
            cameraBtn.setVisibility(View.VISIBLE);
        } else{
            shareBtn.setVisibility(View.INVISIBLE);
            imageBtn.setVisibility(View.INVISIBLE);
            cameraBtn.setVisibility(View.INVISIBLE);
        }

        shareBtn.setClickable(!clicked);
        cameraBtn.setClickable(!clicked);
        imageBtn.setClickable(!clicked);
    }


    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        Log.i("zzz", currentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.i("zzz", "file created");
            } catch (IOException ex) {

            }

            if (photoFile !=null){
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.demo.logandsaveactivity",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }





    @Override
    protected void onStart() {
        writeCall("onStart", this);
        super.onStart();

    }

    @Override
    protected void onResume() {
        writeCall("onResume", this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        writeCall("onPause", this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        writeCall("onStop", this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        writeCall("onDestroy", this);
        super.onDestroy();
    }


}
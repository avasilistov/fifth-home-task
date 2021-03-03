package com.demo.logandsaveactivity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

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
    private FloatingActionButton shareBtn;
    private FloatingActionButton addBtn;
    private FloatingActionButton cameraBtn;
    private FloatingActionButton imageBtn;
    private Boolean clicked = false;
    private Animation rotateOpen ;
    private Animation rotateClose ;
    private Animation fromBottom;
    private Animation toBottom;


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
                Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_SHORT).show();
            }
        });

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Image", Toast.LENGTH_SHORT).show();
            }
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
package com.demo.logandsaveactivity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton shareButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File filePath = new File(getBaseContext().getFilesDir(), "");
                File newFile = new File(filePath, "Log.txt");
                Uri contentUri = getUriForFile(getBaseContext(), "com.demo.logandsaveactivity", newFile);
                Intent shareFileIntent = new Intent();
                shareFileIntent.setAction(Intent.ACTION_SEND);
                shareFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareFileIntent.setType("text/plain");
                shareFileIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(Intent.createChooser(shareFileIntent, "Share"));
            }
        });


        writeCall("onCreate");

    }

    @Override
    protected void onStart() {
        writeCall("onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        writeCall("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        writeCall("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        writeCall("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        writeCall("onDestroy");
        super.onDestroy();
    }

    private void writeCall(String phase){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        StringBuilder builder = new StringBuilder();
        builder.append(phase);
        builder.append(" - ");
        builder.append(currentDateandTime);
        builder.append("\n");
        try(FileOutputStream fos = this.openFileOutput("Log.txt", Context.MODE_APPEND)) {

            fos.write(builder.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
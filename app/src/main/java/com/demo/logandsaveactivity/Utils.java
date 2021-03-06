package com.demo.logandsaveactivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static final String date_pattern = "yyyy-MM-dd_HH:mm:ss";
    public static final String file_name = "Log.txt";


    // Write down the state in the file Log.txt
    public static void writeCall(String phase, Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat(date_pattern);
        String currentDateandTime = sdf.format(new Date());
        StringBuilder builder = new StringBuilder();
        builder.append(phase);
        builder.append(" - ");
        builder.append(currentDateandTime);
        builder.append("\n");
        try (FileOutputStream fos = context.openFileOutput(file_name, Context.MODE_APPEND)) {

            fos.write(builder.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File createImageFile(Context context, String [] paths) throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        paths[0] = image.getAbsolutePath();

        return image;
    }

    public static void dispatchTakePictureIntent(Context context, ActivityResultLauncher launcher, String [] paths) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(context, paths);
                Log.i("zzz", "file created");
            } catch (IOException ex) {

            }

            if (photoFile !=null){
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.demo.logandsaveactivity.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                launcher.launch(takePictureIntent);
            }
        }
    }

    public static void getAndShow(@Nullable Intent data, Context context, ImageView imgContainer) {
        String picturePath = "";
        if (data != null) {
            Uri selectedImage = data.getData();
            Log.i("zzz", "Uri selected image");
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                Log.i("zzz", "cursor");
                if (cursor != null) {
                    Log.i("zzz", "cursor not null");
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    Log.i("zzz", "" + columnIndex);
                    picturePath = cursor.getString(columnIndex);
                    Log.i("zzz", picturePath);
                    Glide.with(context).load(picturePath).fitCenter().into(imgContainer);
                    cursor.close();
                }
            }
        }

    }



}

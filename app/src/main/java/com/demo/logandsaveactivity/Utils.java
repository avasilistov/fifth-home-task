package com.demo.logandsaveactivity;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static final String date_pattern = "yyyy-MM-dd_HH:mm:ss";
    public static final String file_name = "Log.txt";

    // Write down the state in the file Log.txt
    public static void writeCall(String phase, Context context){
        SimpleDateFormat sdf = new SimpleDateFormat(date_pattern);
        String currentDateandTime = sdf.format(new Date());
        StringBuilder builder = new StringBuilder();
        builder.append(phase);
        builder.append(" - ");
        builder.append(currentDateandTime);
        builder.append("\n");
        try(FileOutputStream fos = context.openFileOutput(file_name, Context.MODE_APPEND)) {

            fos.write(builder.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

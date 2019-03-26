package com.example.badanie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnstart)
    Button btnstart;

    @BindView(R.id.btn_download)
    Button btn_download;

// metoda startu przechodząca do kolejnej aktywności
    @OnClick(R.id.btnstart)
    void onClick(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
        isWriteStoragePermissionGranted();
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + Environment.DIRECTORY_DCIM+"/results");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }
    }

    @OnClick(R.id.btn_download)
    void onClick1(View view) {
        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM+"/results"));
        File root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM+"/results");
        File gpxfile = new File(root, "all.csv");
        FileWriter writer = null;
        try {
            writer = new FileWriter(gpxfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        File fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM+"/results");
        Log.d("Files", "Size: "+ files.length);
        List<String> paths=new ArrayList<>();
        List<String> allTimes = new ArrayList<>();
        for (int i = 0; i < files.length; i++)
        {
            paths.add(files[i].getName());
            //Log.d("Files", "FileName:" + files[i].getName());
            File fileToGet = new File(fileDirectory,paths.get(i).toString());
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileToGet));
                String line;
                while ((line = br.readLine()) !=null) {

                    allTimes.add(line);
                    writer.append("\n");
                    writer.append(line);

                }
            }catch (FileNotFoundException e) {
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            }

        }

        try {
            
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }











    public List<String> tasks = new ArrayList<>();
    private void readData() {
        InputStream is=getResources().openRawResource(R.raw.dane);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            while((line=reader.readLine()) !=null){
                tasks.add(line);

                Log.d("MyActivity","Just created:"+line);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }

    }


    private void isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

    }
}

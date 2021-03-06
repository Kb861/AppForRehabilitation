package com.example.badanie.Main;

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
import android.widget.Toast;

import com.example.badanie.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnstart)
    Button btnstart;

    @BindView(R.id.btn_download)
    Button btn_download;


    @OnClick(R.id.btnstart)
    void onClick(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);

        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + Environment.DIRECTORY_DCIM + "/results");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {

        } else {

        }
    }

    @OnClick(R.id.btn_download)
    void onClick1(View view) {
        readnumberofTasks();
        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM + "/results"));
        File root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM + "/results");
        File gpxfile = new File(root, "all.csv");
        FileWriter writer = null;
        try {
            writer = new FileWriter(gpxfile);
            writer.append("ID"+";");
            for (int i = 0; i < noTasks; i++)
            {
                writer.append("Zad"+ (i+1)+";");
            }
            writer.append("miedzyczas");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        File fileDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/results");
        Log.d("Files", "Size: " + files.length);
        List<String> paths = new ArrayList<>();


        for (int i = 0; i < files.length; i++) {

            paths.add(files[i].getName());
            File fileToGet = new File(fileDirectory, paths.get(i));
            try {
                writer.append("\n");
                BufferedReader br = new BufferedReader(new FileReader(fileToGet));
                String line;
                while ((line = br.readLine()) != null) {

                        if(line.length()>13)
                        {
                        line=line.substring(6);
                        writer.append(line);
                        writer.append(";");
                        }
                        else
                        {writer.append(line);
                            writer.append(";");}
                }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(MainActivity.this, "Plik zbiorczy został utworzony", Toast.LENGTH_SHORT).show();
    }

    private void isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        isWriteStoragePermissionGranted();
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
    }


    private int noTasks=0;
    private void readnumberofTasks() {
        InputStream is=getResources().openRawResource(R.raw.dane);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            while((line=reader.readLine()) !=null){
                noTasks=noTasks+1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package com.example.badanie;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by patry on 18.03.2019.
 */

public class SummaryActivity extends AppCompatActivity {


    @BindView(R.id.EtNotes)
    EditText EtNotes;

    @BindView(R.id.tasks)
    TextView tasks;

    @BindView(R.id.btn_Finish)
    Button btn_Finish;

    @BindView(R.id.btnHome)
    Button btnHome;

    Dialog epicDialog;
    ImageView closedialog;


    private void isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
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
    @OnClick(R.id.btnHome)
     void onClick2(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
        isWriteStoragePermissionGranted();
        String entry = EtNotes.getText().toString();
        String entry_req = tasks.getText().toString();
        Save("105",entry,entry_req);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        isReadStoragePermissionGranted();
        epicDialog=new Dialog(this);
        ShowBox();
        Bundle dataFromStopwatchActivity = getIntent().getExtras();
        String text = dataFromStopwatchActivity.getString("KEY");
        String ReqText = dataFromStopwatchActivity.getString("KEY_ZAD");
        EtNotes.setText(text);
        tasks.setText(ReqText);

    }

    private void Save(String id, String dane, String dane2) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            File root = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM);
            File gpxfile = new File(root, id +" " + timeStamp+".csv");
            FileWriter writer = new FileWriter(gpxfile);

            writer.append(dane);
            writer.append('\n');
            writer.append(dane2);
            writer.append('\n');
            writer.flush();
            writer.close();
            Toast.makeText(SummaryActivity.this, "Success", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(SummaryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.btn_Finish)
    void onClick(View view){
            String entry = EtNotes.getText().toString();
            String entry_req = tasks.getText().toString();
            Save("105",entry,entry_req);

    }
    public void ShowBox(){
        epicDialog.setContentView(R.layout.dialog_box);
        closedialog=(ImageView) epicDialog.findViewById(R.id.close_box) ;
        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }



}
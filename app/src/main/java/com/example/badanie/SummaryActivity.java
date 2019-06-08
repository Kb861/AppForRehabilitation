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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badanie.Main.MainActivity;
import com.example.badanie.Main.StartActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummaryActivity extends AppCompatActivity {


    @BindView(R.id.EtNotes)
    EditText EtNotes;

    @BindView(R.id.tasks)
    TextView tasks;

    @BindView(R.id.tasks_id)
    TextView tasks_id;

    @BindView(R.id.btn_Finish)
    Button btn_Finish;

    @BindView(R.id.btnHome)
    Button btnHome;

    private Dialog epicDialog;


    private void isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }
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
    @OnClick(R.id.btnHome)
     void onClick2(View view) {
        isWriteStoragePermissionGranted();
        String entry = EtNotes.getText().toString();
        String entry_req = tasks.getText().toString();
        String entry_id = tasks_id.getText().toString();
        Save(entry_id,entry,entry_req);
        ShowBox("MainActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        isReadStoragePermissionGranted();
        epicDialog=new Dialog(this);
        Bundle dataFromStopwatchActivity = getIntent().getExtras();
        String text = dataFromStopwatchActivity.getString("KEY");
        String ReqText = dataFromStopwatchActivity.getString("KEY_ZAD");
        String IdText = dataFromStopwatchActivity.getString("KEY_ID");
        tasks_id.setText(IdText);
        EtNotes.setText(text);
        tasks.setText(ReqText);
        EtNotes.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (EtNotes.length() == 0) {
                ((EditText) view).setSelection(0);
            }
            else
            {
                ((EditText) view).setSelection(14);
            }
        }
    };

    private void Save(String id, String dane, String dane2) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(Calendar.getInstance().getTime());
            File root = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM+"/results");
            File gpxfile = new File(root, id +" " + timeStamp+".csv");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(id);
            writer.append('\n');
            writer.append(dane2);
            writer.append('\n');
            writer.append(dane);
            writer.append('\n');
            writer.flush();
            writer.close();
            Toast.makeText(SummaryActivity.this, "Zapisano pomyślnie", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(SummaryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.btn_Finish)
    void onClick(View view){

            String entry = EtNotes.getText().toString();
            String entry_req = tasks.getText().toString();
            String entry_id = tasks_id.getText().toString();
            Save(entry_id,entry,entry_req);
            ShowBox("StartActivity");
    }

    private void ShowBox(final String name){
        epicDialog.setContentView(R.layout.dialog_box);
        ImageView closedialog = (ImageView) epicDialog.findViewById(R.id.close_box);
        closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Objects.equals(name, "StartActivity"))
                {Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                    epicDialog.dismiss();}
                if(Objects.equals(name, "MainActivity"))
                {Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                    epicDialog.dismiss();}
            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }



}
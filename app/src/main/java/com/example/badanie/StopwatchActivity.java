package com.example.badanie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.badanie.Models.Chronometer;
import com.example.badanie.Models.Query;

import java.io.BufferedReader;
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

public class StopwatchActivity extends AppCompatActivity {
    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextViewTime.setText(timeAsText);
            }
        });
    }
    @BindView(R.id.btn_Back)
    Button btn_Back;

    @BindView(R.id.btn_Go)
    Button btn_Go;

    @BindView(R.id.btn_Next)
    Button btn_Next;

    @BindView(R.id.TextViewTime)
    TextView TextViewTime;

    @BindView(R.id.TVName)
    TextView TVName;

    @BindView(R.id.tvSaveTimeLap)
    TextView tvSaveTimeLap;

    @BindView(R.id.et_laps)
    EditText et_laps;

    @BindView(R.id.sv_lap)
    ScrollView sv_lap;

    @BindView(R.id.close)
    ImageView close;

    private int count=0;
    private int mLapCounter = 1;

    private Chronometer Chrono;
    private Thread ThreadChrono;
    private Context Context;

    private final List<Query> taskTime=new ArrayList<>();

    @OnClick(R.id.btn_Back)
    void onClick(View view) {
        if (Chrono != null) {
            Chrono.stop();
            ThreadChrono.interrupt();
            ThreadChrono = null;
            Chrono = null;
        }
    }

    @OnClick(R.id.close)
    void onClickClose(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Czy na pewno chcesz zakończyć?")
                .setCancelable(false)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(int i=count+1;i<tasks.size();i++)
                        {
                            tvSaveTimeLap.append("00:00:00" + "\n");
                        }
                        if (Chrono != null) {

                            Chrono.stop();
                            ThreadChrono.interrupt();
                            ThreadChrono = null;
                            Chrono = null;
                        }
                        Intent intent = new Intent(StopwatchActivity.this, SummaryActivity.class);
                        Bundle bundle = new Bundle();
                        String allLaps = et_laps.getText().toString();
                        bundle.putString("KEY", allLaps);
                        intent.putExtras(bundle);
                        String Request = tvSaveTimeLap.getText().toString();
                        bundle.putString("KEY_ZAD", Request);
                        intent.putExtras(bundle);
                        Bundle dataFromStartActivity = getIntent().getExtras();
                        String Id = dataFromStartActivity.getString("KEY_ID");
                        bundle.putString("KEY_ID", Id);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();



    }

    @OnClick(R.id.btn_Next)
    void onClickNext(View view) {
        if (Chrono == null) {
            Toast.makeText(Context, "nic", Toast.LENGTH_SHORT).show();
            return;
        }

       et_laps.append("M " + String.valueOf(mLapCounter++)
                + "   " + TextViewTime.getText() + "\n");

        sv_lap.post(new Runnable() {
            @Override
            public void run() {
                sv_lap.smoothScrollTo(0, et_laps.getBottom());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        ButterKnife.bind(this);
        Context = this;
        readData();
        TVName.setText(tasks.get(0));
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        tvSaveTimeLap.append(timeStamp + "\n");


        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count>0){
                    count=count-1;
                if (count < tasks.size()) {

                    String czas = tvSaveTimeLap.getText().toString();
                    czas=czas.substring(0,tvSaveTimeLap.length() - 17);
                    tvSaveTimeLap.setText(czas);
                    TVName.setText(tasks.get(count));


                }}
            }


        });
        btn_Go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                count = count + 1;
                Query task=new Query();
                task.setTime(String.valueOf(TextViewTime.getText()));
                task.setTaskNumber(count+1);
                taskTime.add(task);
                if (count < tasks.size()) {
                    TVName.setText(tasks.get(count));
                    tvSaveTimeLap.append(TextViewTime.getText() + "\n");
                }
                if (count == tasks.size()) {
                    if (Chrono != null) {

                        Chrono.stop();
                        ThreadChrono.interrupt();
                        ThreadChrono = null;
                        Chrono = null;
                    }
                    Intent intent = new Intent(StopwatchActivity.this, SummaryActivity.class);
                    Bundle bundle = new Bundle();

                    String text = et_laps.getText().toString();
                    bundle.putString("KEY", text);
                    intent.putExtras(bundle);
                    String Request = tvSaveTimeLap.getText().toString();
                    bundle.putString("KEY_ZAD", Request);
                    intent.putExtras(bundle);
                    Bundle dataFromStartActivity = getIntent().getExtras();
                    String Id = dataFromStartActivity.getString("KEY_ID");
                    bundle.putString("KEY_ID", Id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }

        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Chrono == null) {

            Chrono = new Chronometer(Context);
            ThreadChrono = new Thread(Chrono);
            ThreadChrono.start();
            Chrono.start();
            et_laps.setText("");
            mLapCounter = 1;
        }
    }
    @Override
    public void onBackPressed() {
    }

    private final List<String> tasks = new ArrayList<>();
    private void readData() {
        InputStream is=getResources().openRawResource(R.raw.dane);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            while((line=reader.readLine()) !=null){
                tasks.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
package com.example.badanie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by patry on 17.03.2019.
 */

public class StopwatchActivity extends AppCompatActivity {

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
    int mLapCounter = 1;

    Chronometer Chrono;
    Thread ThreadChrono;
    Context Context;
    //lista dla czasu poszczególnych zadań
    public List<Query> taskTime=new ArrayList<>();


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
        if (Chrono != null) {

            Chrono.stop();
            ThreadChrono.interrupt();
            ThreadChrono = null;
            Chrono = null;
        }
        Intent intent = new Intent(this, SummaryActivity.class);
        Bundle bundle = new Bundle();
        String allLaps = et_laps.getText().toString();
        bundle.putString("KEY", allLaps);
        //intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.btn_Next)
    void onClickNext(View view) {
        if (Chrono == null) {
            Toast.makeText(Context, "nic", Toast.LENGTH_SHORT).show();
            return; //do nothing!
        }
        //we just simply copy the current text of tv_timer and append it to et_laps

       et_laps.append("LAP " + String.valueOf(mLapCounter++)
                + "   " + TextViewTime.getText() + "\n");

        //scroll to the bottom of et_laps
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


        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count>0){
                    count=count-1;
                if (count < tasks.size()) {

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
                    tvSaveTimeLap.append("Zad " + String.valueOf(count)
                    + "   " + TextViewTime.getText() + "\n");
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
                    //intent.putExtras(bundle);

                    String wpisanyTekst = et_laps.getText().toString();
                    bundle.putString("KEY", wpisanyTekst);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }

            }

        });

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        //starttime
        if (Chrono == null) {
            //instantiate the chronometer
            Chrono = new Chronometer(Context);
            //run the chronometer on a separate thread
            ThreadChrono = new Thread(Chrono);
            ThreadChrono.start();

            //start the chronometer!
            Chrono.start();

            //clear the perilously populated et_laps
            et_laps.setText(""); //empty string!

            //reset the lap counter
            mLapCounter = 1;
        }

    }


    public void updateTimerText(final String timeAsText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextViewTime.setText(timeAsText);
            }
        });
    }

    //metoda wczytuje listę zadań (plik.csv)
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


}
package com.example.badanie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by patry on 17.03.2019.
 */

public class StopwatchActivity extends AppCompatActivity {

    @BindView(R.id.btn_Start)
    Button btn_Start;

    @BindView(R.id.btn_Next)
    Button btn_Next;

    @BindView(R.id.TextViewTime)
    TextView TextViewTime;

    @BindView(R.id.TVName)
    TextView TVName;

    @BindView(R.id.et_laps)
    EditText et_laps;

    @BindView(R.id.sv_lap)
    ScrollView sv_lap;

    int mLapCounter = 1;

    Chronometer Chrono;


    Thread ThreadChrono;

    Context Context;

    @OnClick(R.id.btn_Start)
    void onClick(View view) {
        if (Chrono != null) {

            Chrono.stop();
            ThreadChrono.interrupt();
            ThreadChrono = null;
            Chrono = null;
        }
    }

    @OnClick(R.id.btn_Next)
    void onClickNext(View view) {
        if (Chrono == null) {
            Toast.makeText(Context
                    , "nic", Toast.LENGTH_SHORT).show();
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


    public List<String> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        ButterKnife.bind(this);
        Context = this;
        tasks.add("Zadanie1");
        tasks.add("Zadanie2");
        tasks.add("Zadanie3");
        TVName.setText(tasks.get(0));
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


}

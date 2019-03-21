package com.example.badanie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    public String saveLap;

    Chronometer Chrono;


    Thread ThreadChrono;

    Context Context;

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
        intent.putExtras(bundle);
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


    public List<String> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        ButterKnife.bind(this);
        Context = this;
        tasks.add("Zadanie1fxhfhfgdjfdgjdfhftjfdtjfthfhf");
        tasks.add("Zadanie2");
        tasks.add("Zadanie3");

        btn_Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<tasks.size())
                {
                    TVName.setText(tasks.get(count+1));
                  
                }

                if(count==3)
                {

                    Intent intent = new Intent(StopwatchActivity.this, SummaryActivity.class);
                    startActivity(intent);
                }

                count=+1;

            }

        }       );














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
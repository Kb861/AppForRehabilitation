package com.example.badanie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by patry on 17.03.2019.
 */

public class StopwatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
    }

    @BindView(R.id.btn_Start)
    Button btn_Start;

    @BindView(R.id.btn_Next)
    Button btn_Next;

    @BindView(R.id.btn_Pause)
    Button btn_Pause;
}

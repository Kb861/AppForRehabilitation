package com.example.badanie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by patry on 17.03.2019.
 */

public class StopwatchActivity extends AppCompatActivity {

    @BindView(R.id.btn_Start)
    Button btn_Start;

    @BindView(R.id.btn_Next)
    Button btn_Next;



    @BindView(R.id.TVName)
    TextView TVName;

    public List<String> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        ButterKnife.bind(this);

        tasks.add("Zadanie1");
        tasks.add("Zadanie2");
        tasks.add("Zadanie3");
        TVName.setText(tasks.get(0));

    }


}

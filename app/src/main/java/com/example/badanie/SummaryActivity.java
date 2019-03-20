package com.example.badanie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by patry on 18.03.2019.
 */

public class SummaryActivity extends AppCompatActivity {
    @BindView(R.id.nameid)
    TextView nameid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        Bundle id = getIntent().getExtras();
        String textID = id.getString("KEY");
        nameid.setText(textID);
    }
}
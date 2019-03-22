package com.example.badanie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by patry on 18.03.2019.
 */

public class SummaryActivity extends AppCompatActivity {


    @BindView(R.id.EtNotes)
    EditText EtNotes;

    @BindView(R.id.btn_Finish)
    Button btn_Finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);

        Bundle dataFromStopwatchActivity = getIntent().getExtras();
        String text = dataFromStopwatchActivity.getString("KEY");
        EtNotes.setText(text);

    }

    @OnClick(R.id.btn_Finish)
    void onClick(View view){
        if(EtNotes.length() > 250)
        {
            Toast.makeText(SummaryActivity.this, "Za długi komentarz! ", Toast.LENGTH_SHORT).show();
        }

    }



}
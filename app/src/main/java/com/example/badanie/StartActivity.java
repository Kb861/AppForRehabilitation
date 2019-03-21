package com.example.badanie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.EtID)
    EditText EtID;

    @BindView(R.id.btn_ID)
    Button btn_ID;

    @OnClick(R.id.btn_ID)
    void onClick(View view) {
        Intent intent = new Intent(this, StopwatchActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    private List<Query> queryList = new ArrayList<>();
    private void readData() {
        InputStream is=getResources().openRawResource(R.raw.dane);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            while((line=reader.readLine()) !=null){
                String[] token=line.split(",");
                Query query=new Query();
                query.setName(line);
                queryList.add(query);

                Log.d("MyActivity","Just created:"+query);
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }

    }
}

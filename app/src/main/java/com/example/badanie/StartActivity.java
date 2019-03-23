package com.example.badanie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.EtID)
    EditText EtID;

    @BindView(R.id.btn_ID)
    Button btn_ID;

    // metoda wczytująca i sprawdzająca id
    @OnClick(R.id.btn_ID)
    void onClick(View view) {

        String value= EtID.getText().toString();
        if (!value.isEmpty())
        {
            int finalValue=Integer.parseInt(value);

            if ( finalValue >=101 && finalValue<=201 )
            {
                Intent intent = new Intent(this, CheckActivity.class);
                Intent intent2 = new Intent(StartActivity.this, SummaryActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);

          /*  String wpisaneID = EtID.getText().toString();
            bundle.putString("KEY_ID", wpisaneID);
            intent2.putExtras(bundle);*/

                startActivity(intent);

            }
            else{
                Toast.makeText(StartActivity.this, "Wprowadzono złe ID!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(StartActivity.this, "Należy wprowadzić ID użytkownika!", Toast.LENGTH_SHORT).show();

        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }



}

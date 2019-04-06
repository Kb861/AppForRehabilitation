package com.example.badanie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

    @OnClick(R.id.btn_ID)
    void onClick(View view) {

        String value = EtID.getText().toString();
        if (!value.isEmpty()) {
            int finalValue = Integer.parseInt(value);
            Intent intent = new Intent(this, CheckActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            String Id = EtID.getText().toString();
            bundle.putString("KEY_ID", Id);
            intent.putExtras(bundle);
            startActivity(intent);


        } else {
            Toast.makeText(StartActivity.this, "Należy wprowadzić ID użytkownika!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SupportActionBarBack();

    }
    public void SupportActionBarBack() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            this.finish();
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        
    }

}

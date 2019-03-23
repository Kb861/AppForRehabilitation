package com.example.badanie;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by patry on 22.03.2019.
 */

public class CheckActivity extends AppCompatActivity {

    @BindView(R.id.btn_show)
    Button btn_show;
    @BindView(R.id.my_recycler_view)
    RecyclerView my_recycler_view;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Czujnik 1"));
        itemList.add(new Item("Czujnik 2"));
        itemList.add(new Item("Czujnik 3"));

        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(itemList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btn_show)
    public void Exit() {
        int count=0;
        List<Item> stList = ((ItemAdapter) mAdapter).getitemList();
        for (int i = 0; i < stList.size(); i++) {
            Item sItem = stList.get(i);
            if (sItem.isSelected()) {
                count = count + 1;
            }
        }
        if(count==stList.size())
        {Intent intent = new Intent(CheckActivity.this, StopwatchActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            startActivity(intent);}
        else
        {Toast.makeText(CheckActivity.this, "Pamiętaj o wszystkich czujnikach! ", Toast.LENGTH_SHORT).show();}

    }


}

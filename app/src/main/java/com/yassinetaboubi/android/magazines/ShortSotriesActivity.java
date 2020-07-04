package com.yassinetaboubi.android.magazines;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yassinetaboubi.android.magazines.R;

import java.util.ArrayList;

public class ShortSotriesActivity extends AppCompatActivity {

    private static final String TAG = "ShortSotriesActivity";
    private Context mContext;

    ArrayList<String> titleArrayList;
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_sotries);

        mContext  =  ShortSotriesActivity.this;
        titleArrayList = new ArrayList <String>();

        titleArrayList.add(Constants.The_Boy_Who_Cried_Wolf);
        titleArrayList.add(Constants.The_Golden_Egg);
        titleArrayList.add(Constants.The_Midas_Touch);
        titleArrayList.add(Constants.The_Miser_And_His_Gold);

        mRecycleView = (RecyclerView) findViewById(R.id.title_layout_recyclerView);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        TitleAdapter adapter = new TitleAdapter(mContext, titleArrayList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent desIntent = new Intent(mContext, DescriptionActivity.class);
                desIntent.putExtra("titles", titleArrayList.get(position));
                startActivity(desIntent);


            }
        });

        mRecycleView.setAdapter(adapter);

    }
}

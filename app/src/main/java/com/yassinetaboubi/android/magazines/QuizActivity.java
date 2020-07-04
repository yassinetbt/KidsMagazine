package com.yassinetaboubi.android.magazines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yassinetaboubi.android.magazines.R;

public class QuizActivity extends AppCompatActivity {
    LinearLayout color;
    LinearLayout math;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizs);
        color = findViewById(R.id.color);
        math = findViewById(R.id.math);
        Intent i=getIntent();
        final String m= i.getStringExtra("n");
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, VActivity.class);
                i.putExtra("name",m);
               // Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, ColorActivity.class);

                startActivity(i);
            }
        });
    }
}

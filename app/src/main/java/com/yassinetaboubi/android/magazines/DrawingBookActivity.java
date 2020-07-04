package com.yassinetaboubi.android.magazines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.yassinetaboubi.android.magazines.R;

public class DrawingBookActivity extends AppCompatActivity {

    private CanvasView canvasView;
    SeekBar bar;
    Button black;
    Button red;
    Button blue;
    Button yellow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_book);
        bar = findViewById(R.id.seekBar2);
        canvasView = (CanvasView) findViewById(R.id.canvas);
        black = findViewById(R.id.black);
        red = findViewById(R.id.red);
        blue = findViewById(R.id.blue);
        yellow = findViewById(R.id.yellow);

        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.changeColor("black");
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.changeColor("red");
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.changeColor("blue");
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.changeColor("yellow");
            }
        });
        bar.setProgress(10);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                canvasView.changeSize(bar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void clearCanvas(View view){
        canvasView.clearCanvas();
    }
}

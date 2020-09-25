package com.yassinetaboubi.android.magazines;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yassinetaboubi.android.magazines.R;

public class ColorActivity extends AppCompatActivity {
//git
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
    }
    public void sayTheColor(View view){
        ImageView imageView=(ImageView) view;

        MediaPlayer mediaPlayer=MediaPlayer.create(this,
                getResources().getIdentifier(imageView.getTag().toString(),"raw",getPackageName()));
        mediaPlayer.start();

    }
}

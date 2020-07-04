package com.yassinetaboubi.android.magazines;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yassinetaboubi.android.magazines.R;
import com.yassinetaboubi.android.magazines.retrofit.INodeJS;
import com.yassinetaboubi.android.magazines.retrofit.RetrofitClient;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GameActivity extends AppCompatActivity {

    ImageView blue,red,green;
    private float blueX,blueY;
    private float redX,redY;
    private float greenX,greenY;
    private Timer timer;
    private Handler handler=new Handler();
    private boolean start=false;
    private boolean action=false;
    private boolean pink=false;
    Button startg;
    Button quitg;
    private int frameHeight,frameWidth,initialFrameWidth;
    private int timeCount;
    private ConstraintLayout constraintLayout;
    private TextView scoreBalloon;
    int sc=0;
    int sc1=0;
    int sc2=0;
    String ss;
    int s;
    int s1;
    int s2;
    private TextView score;
    String sb;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    INodeJS myAPI;
    String m;
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i=getIntent();
        m= i.getStringExtra("name");

        blue=findViewById(R.id.blue);
        red=findViewById(R.id.red);
        green=findViewById(R.id.green);
        startg=findViewById(R.id.startgame);
        quitg=findViewById(R.id.quitgame);
        constraintLayout=findViewById(R.id.constraint);
        scoreBalloon=findViewById(R.id.scoreballoon);
        score=findViewById(R.id.sc);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        quitg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(GameActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
    public void changePos(){
        greenY+=6;
        blueY+=10;
        redY+=10;
        // float greenCenterX=greenX+green.getWidth()/2;
        // float greenCenterY=greenY+green.getWidth()/2;

        ss=scoreBalloon.getText().toString();
        green.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(start){
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        greenY-=2000;
                        greenX=(float)Math.floor(Math.random()*(frameWidth-green.getWidth()));
                        int k= sc+1;
                        try{
                            s1=Integer.parseInt(ss);
                        }catch (NumberFormatException n){}

                        int sf=s1+1;
                        sb=String.valueOf(sf);
                        scoreBalloon.setText(sb);
                        Log.d("le vert",""+sb);
                    }

                }
                return true;
            }
        });

        blue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                    blueY-=2000;
                blueX=(float)Math.floor(Math.random()*(frameWidth-blue.getWidth()));
                // int k=sc1+2;
                try {
                    s=Integer.parseInt(ss);
                }catch (NumberFormatException n){}

                // Log.d("le nombre",""+k);
                int sf= s+1;
                sb=String.valueOf(sf);
                scoreBalloon.setText(sb);
                Log.d("le score",""+sb);


                return true;
            }
        });
        red.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                    redY-=2000;
                redX=(float)Math.floor(Math.random()*(frameWidth-red.getWidth()));
                // int k=+3;

                try {
                    s2=Integer.parseInt(ss);
                }catch (NumberFormatException n){

                }

                int sf= s2+1;
                sb=String.valueOf(sf);
                scoreBalloon.setText(sb);
                return true;
            }
        });

        //greenY=frameHeight+100;

        if (greenY>frameHeight){
            if(!green.callOnClick()){
                ballonScore(m,scoreBalloon.getText().toString());
                gameOver();

            }
        }

        if(greenY>frameHeight){
            greenY-=2000;
            greenX=(float)Math.floor(Math.random()*(frameWidth-green.getWidth()));


        }
        if(blueY>frameHeight){
            blueY-=2000;
            blueX=(float)Math.floor(Math.random()*(frameWidth-blue.getWidth()));
        }
        if(redY>frameHeight){
            redY-=2000;
            redX=(float)Math.floor(Math.random()*(frameWidth-red.getWidth()));
        }
        green.setX(greenX);
        green.setY(greenY);
        blue.setX(blueX);
        blue.setY(blueY);
        red.setX(redX);
        red.setY(redY);

    }

    public void gameOver(){
        timer.cancel();
        start=false;
        blue.setVisibility(View.INVISIBLE);
        green.setVisibility(View.INVISIBLE);
        red.setVisibility(View.INVISIBLE);
        startg.setVisibility(View.VISIBLE);
        quitg.setVisibility(View.VISIBLE);
        score.setText(sb);
        score.setVisibility(View.VISIBLE);

        scoreBalloon.setVisibility(View.INVISIBLE);


    }

    private void ballonScore(String username, String score) {
        compositeDisposable.add(myAPI.balloonScore(username,score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("score"))
                            Toast.makeText(GameActivity.this, "Login Succes", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(GameActivity.this, " " + s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("7000", throwable.getMessage(), throwable);
                    }
                })
        );

    }

    public void StarGame(View view){
        start=true;
        startg.setVisibility(view.INVISIBLE);
        quitg.setVisibility(view.INVISIBLE);
        score.setVisibility(view.INVISIBLE);
        scoreBalloon.setText("0");

        if(frameHeight==0){
            frameHeight=constraintLayout.getHeight();
            frameWidth=constraintLayout.getWidth();
            initialFrameWidth=frameWidth;
        }


        green.setY(0.0f);
        red.setY(3000.0f);
        blue.setY(500.0f);

        greenY=green.getY();
        redY=red.getY();
        blueY=blue.getY();

        green.setVisibility(view.VISIBLE);
        red.setVisibility(view.VISIBLE);
        blue.setVisibility(view.VISIBLE);
        scoreBalloon.setVisibility(View.VISIBLE);
        timeCount=0;

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(start) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        },0,10);



    }



}

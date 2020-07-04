package com.yassinetaboubi.android.magazines;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yassinetaboubi.android.magazines.R;
import com.yassinetaboubi.android.magazines.retrofit.INodeJS;
import com.yassinetaboubi.android.magazines.retrofit.RetrofitClient;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class VActivity extends AppCompatActivity {
    TextView r1;
    TextView r2;
    EditText sc;
    TextView lsc;
    Button cal;
    String res1;
    String s2;
    TextView name;
    String s3;
    int res = 0;
    int s1 = 0;
    int id;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        setContentView(R.layout.activity_maths);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        r1 = findViewById(R.id.rand1);
        r2 = findViewById(R.id.rand2);
        sc = findViewById(R.id.score);
        cal = findViewById(R.id.calculate);
        lsc = findViewById(R.id.scor);
        name = findViewById(R.id.name);
        Random random = new Random();
        int number = random.nextInt(10) + 1;
        String ra1 = String.valueOf(number);
        r1.setText(ra1);
        Random random1 = new Random();
        int number1 = random1.nextInt(10) + 1;
        String ra2 = String.valueOf(number1);
        r2.setText(ra2);

        res = number + number1;
        res1 = String.valueOf(res);
        Intent i = getIntent();
        String m = i.getStringExtra("name");

        name.setText(m);



        //   lsc.setText(s);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }

        InsertScore(name.getText().toString(), lsc.getText().toString());

        cal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String sc1 = sc.getText().toString();
                if (res1.equals(sc1)) {
                    s1 = s1 + 1;
                    s2 = String.valueOf(s1);
                    lsc.setText(s2);
                    s3 = lsc.getText().toString();
                    //   InsertScore(name.getText().toString(), lsc.getText().toString());
                    UpdateScore(id, lsc.getText().toString());
                    Random random = new Random();
                    int number = random.nextInt(10) + 1;
                    String ra1 = String.valueOf(number);
                    r1.setText(ra1);
                    Random random1 = new Random();
                    int number1 = random1.nextInt(10) + 1;
                    String ra2 = String.valueOf(number1);
                    r2.setText(ra2);
                    res = number + number1;
                    res1 = String.valueOf(res);


                }
            }
        });

    }

    private void UpdateScore( int id,String score) {
        compositeDisposable.add(myAPI.UpdateScore(id,score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("score"))
                            Toast.makeText(VActivity.this, "", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(VActivity.this, " " , Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("7000", throwable.getMessage(), throwable);
                    }
                })
        );
    }

    private void InsertScore(String username, String score) {
        compositeDisposable.add(myAPI.InsertScore(username, score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("score"))
                            Toast.makeText(VActivity.this, "", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(VActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("7000", throwable.getMessage(), throwable);
                    }
                })
        );
    }
}

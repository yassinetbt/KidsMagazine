package com.yassinetaboubi.android.magazines;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.yassinetaboubi.android.magazines.R;
import com.yassinetaboubi.android.magazines.retrofit.INodeJS;
import com.yassinetaboubi.android.magazines.retrofit.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class RegActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mSigninBtn;

    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    private DatabaseReference mDatabase;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    OkHttpClient.Builder builder;
    DatePickerDialog.OnDateSetListener dateSetListener1;
    protected  void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        mNameField =  findViewById(R.id.nameField);
        mEmailField =  findViewById(R.id.emailField);
        mPasswordField =  findViewById(R.id.passwordField);

        mSigninBtn =  findViewById(R.id.signinBtn);

        mAuth = FirebaseAuth.getInstance();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("User");

        mProgress = new ProgressDialog(this);

        mSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
        Retrofit retrofit= RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);
        builder = new OkHttpClient.Builder();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        mPasswordField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        RegActivity.this,dateSetListener1,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });
        dateSetListener1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day + "-" +month+ "-" +year;
                mPasswordField.setText(date);
            }
        };
        mSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNameField.getText().toString().isEmpty()||mPasswordField.getText().toString().isEmpty()||mEmailField.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Complet All Fields",Toast.LENGTH_LONG).show();

                }
                else{
                    //registerUser(mNameField.getText().toString(),mEmailField.getText().toString(),mPasswordField.getText().toString());
                    Intent i=new Intent(RegActivity.this,HomeActivity.class);
                    i.putExtra("user",mNameField.getText().toString());
                    i.putExtra("pass",mEmailField.getText().toString());
                   // Toast.makeText(getApplicationContext(),"Registration Done",Toast.LENGTH_LONG).show();
                    startActivity(i);
                }
            }
        });

    }

    private void registerUser(String username, String password, String datedenaissance) {
        compositeDisposable.add(myAPI.registerUser(username, password, "123")
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {


                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("7000", throwable.getMessage(), throwable);

                    }
                })

        );
    }

    private void startRegister() {
        final String name = mNameField.getText().toString().trim();
        final String email = mEmailField.getText().toString().trim();
        final String password = mPasswordField.getText().toString().trim();

        mProgress.setMessage("Signing Up");
        mProgress.show();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);
                        current_user_db.child("username").setValue(name);

                        mProgress.dismiss();

                        Intent mainIntent = new Intent(RegActivity.this, AccountActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }
    }

}

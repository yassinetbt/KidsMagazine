package com.yassinetaboubi.android.magazines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yassinetaboubi.android.magazines.R;
import com.yassinetaboubi.android.magazines.retrofit.INodeJS;
import com.yassinetaboubi.android.magazines.retrofit.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG="xxx";
    private EditText mLoginNameField;
    private EditText mLoginPasswordField;
    private Button mLoginBtn, cancelBtn;
    private Button regBtn, learn_more;
    static String k="";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    protected  void onStop(){
        compositeDisposable.clear();
        super.onStop();
    }

    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

   /* private Button regBtn, loginBtn, cancelBtn;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        mLoginNameField =  findViewById(R.id.loginNameField);
        mLoginPasswordField =  findViewById(R.id.loginPasswordField);
        SharedPreferences sharedPref = getSharedPreferences(TAG,MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        Intent h= getIntent();
        String un = h.getStringExtra("user");
        String passw = h.getStringExtra("pass");

        mLoginNameField.setText(un);
        mLoginPasswordField.setText(passw);
        mLoginBtn =  findViewById(R.id.loginBtn);
        Retrofit retrofit= RetrofitClient.getInstance();
        myAPI=retrofit.create(INodeJS.class);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!= null){
                    startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                }
            }
        };


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLoginPasswordField.getText().toString().isEmpty()||mLoginNameField.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Complet All Fields",Toast.LENGTH_LONG).show();

                }else{
                    Intent i=new Intent(HomeActivity.this,MainActivity.class);
                    String name=mLoginNameField.getText().toString();
                    char x='"';
                    k=x+"login succes"+x;
                    loginUser(name,mLoginPasswordField.getText().toString());


                    if(k.equals(x+"login succes"+x)){

                        Intent iw=new Intent(HomeActivity.this,MainActivity.class);
                        iw.putExtra("name",name);
                        //editor.putString("username",mLoginNameField.getText().toString());
                        //editor.putString("pass",mLoginPasswordField.getText().toString());
                        //editor.commit();

                        startActivity(iw);}
                    else{
                        k="";
                    }
                    }



            }
        });


        regBtn =  findViewById(R.id.regBtn);
        mLoginBtn =  findViewById(R.id.loginBtn);
        cancelBtn =  findViewById(R.id.cancelBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegistrationActivity();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelActivity();
            }
        });
    }

    private void loginUser(String username, String password) {

        compositeDisposable.add(myAPI.loginUser(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        k=s;

                        //Toast.makeText(HomeActivity.this, s ,Toast.LENGTH_SHORT).show();

                    }
                },new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e("7000", throwable.getMessage(), throwable);
                    }
                })
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

  /*  private void startSignIn() {
        String email = mLoginNameField.getText().toString().trim();
        String password = mLoginPasswordField.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(HomeActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(HomeActivity.this, "Sign in problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }*/

    private void startRegistrationActivity() {
        Intent intent = new Intent(HomeActivity.this, RegActivity.class);
        startActivity(intent);
    }

    private void cancelActivity() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

package com.yassinetaboubi.android.magazines.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(){
        if(instance==null)

            instance=new Retrofit.Builder().baseUrl("http://41.226.11.252:11885/")//In Emulator 127.0.0.1 will changed to 10.0.0.2
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;
    }
}

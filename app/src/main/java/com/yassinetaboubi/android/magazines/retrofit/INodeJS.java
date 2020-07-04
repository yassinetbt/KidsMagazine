package com.yassinetaboubi.android.magazines.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {


    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("username") String username,
                                    @Field("password")String password,
                                    @Field("datedenaissane") String datedenaissance);
    @POST("login")
    @FormUrlEncoded
    Observable<String>loginUser(@Field("username") String username,
                                @Field("password")String password);
    @POST("insert")
    @FormUrlEncoded
    Observable<String>InsertScore(
            @Field("username")String username, @Field("score")String score);
    @POST("update")
    @FormUrlEncoded
    Observable<String>UpdateScore(
            @Field("id")int id, @Field("score")String score);
    @POST("score")
    @FormUrlEncoded
    Observable<String>InsertScore1(
            @Field("username")String username, @Field("score")String score);

    @POST("/balloon/")
    @FormUrlEncoded
    Observable<String>balloonScore(@Field("username")String username,@Field("score")String score);


}

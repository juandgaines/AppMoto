package com.mytechideas.appmoto.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppMotoRetrofitinstance{

    private static final Object LOCK =new Object();
    private static Retrofit sInstance;



    private static final String BASE_URL_RETROFIT="http://safetrips.co:3000/";

    private static Retrofit getRetroInstance(){
        if(sInstance==null){
            synchronized (LOCK){
                Gson gson= new GsonBuilder().create();
                sInstance=new Retrofit.Builder()
                        .baseUrl(BASE_URL_RETROFIT)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build();
            }
        }
        return sInstance;
    }

    public static AppMotoServiceCall getAppMotoService(){
        return getRetroInstance().create(AppMotoServiceCall.class);
    }


}

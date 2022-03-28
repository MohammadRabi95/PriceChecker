package com.invoke.pricechecker.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstances {

    private static Retrofit retrofit;
    private static final String baseUrl=" http://localhost:5000/api/";

    public static Retrofit getRetrofit() {
        if (retrofit==null)
        {
            retrofit=new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}

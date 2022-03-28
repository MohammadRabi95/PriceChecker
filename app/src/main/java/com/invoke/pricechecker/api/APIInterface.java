package com.invoke.pricechecker.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface APIInterface {

    @PUT("Stockdbf")
    Call<ResponseBody> getData(@Body RequestBody requestBody);
}

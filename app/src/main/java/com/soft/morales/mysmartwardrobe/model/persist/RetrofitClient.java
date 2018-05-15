package com.soft.morales.mysmartwardrobe.model.persist;

import com.soft.morales.mysmartwardrobe.model.Garment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitClient {

    // Trailing slash is needed
    public static final String BASE_URL = "https://next.json-generator.com/api/json/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    // Request method and URL specified in the annotation

    @GET("get/EyBuc_mRN")
    Call<List<Garment>> getGarment();

    @POST("users/new")
    Call<Garment> createGarment(@Body Garment garment);


}

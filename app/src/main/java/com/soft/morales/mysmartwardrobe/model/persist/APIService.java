package com.soft.morales.mysmartwardrobe.model.persist;

import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIService {

    // Request method and URL specified in the annotation

    @GET("garments/")
    Call<List<Garment>> getGarment();

    @GET("garments/")
    Call<List<Garment>> getGarment(@QueryMap Map<String, String> options);

    @DELETE("garments/{id}/")
    Call<Garment> deleteGarment(@Path("id") String id);

    @POST("garments/")
    @FormUrlEncoded
    Call<Garment> savePost(
            @Field("name") String name,
            @Field("photo") String photo,
            @Field("category") String category,
            @Field("season") String season,
            @Field("price") String price,
            @Field("username") String username,
            @Field("color") String color,
            @Field("size") String size,
            @Field("brand") String brand);

    @POST("users/")
    @FormUrlEncoded
    Call<User> createAccount(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("age") String age);

    @GET("users/")
    Call<List<User>> loginUser();

    @POST("brands/")
    @FormUrlEncoded
    Call<Garment> createBrand(
            @Field("name") String name);


}

package com.soft.morales.mysmartwardrobe.model.persist;

import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.Look;
import com.soft.morales.mysmartwardrobe.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface APIService {

    // Request method and URL specified in the annotation

    @GET("garments/")
    Call<List<Garment>> getGarment(@QueryMap Map<String, String> options);


    @GET("garments/{id}/")
    Call<Garment> getGarment(@Path("id") Integer id);


    @DELETE("garments/{id}/")
    Call<Garment> deleteGarment(@Path("id") Integer id);


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


    @POST("looks/")
    @FormUrlEncoded
    Call<Look> sendLook(
            @Field("garment_id") List<Integer> garment_id,
            @Field("username") String username,
            @Field("date") String date);


    @GET("looks/")
    Call<List<Look>> getLooks(@QueryMap Map<String, String> options);


    @DELETE("looks/{id}/")
    Call<Look> deleteLook(@Path("id") Integer id);

    @Multipart
    @PUT("users/{id}/")
    Call<User> updateUser(@Part("garments") String garmentId);

}

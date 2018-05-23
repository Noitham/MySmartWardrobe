package com.soft.morales.mysmartwardrobe.model.persist;

import com.soft.morales.mysmartwardrobe.model.Garment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    // Request method and URL specified in the annotation

    @GET("garments/")
    Call<List<Garment>> getGarment();

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
            @Field("color") String color,
            @Field("size") String size,
            @Field("brand") String brand);

    @POST("brands/")
    @FormUrlEncoded
    Call<Garment> createBrand(
            @Field("name") String name);


}

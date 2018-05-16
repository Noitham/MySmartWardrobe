package com.soft.morales.mysmartwardrobe.model.persist;

public class ApiUtils {

    private ApiUtils() {}

    // Trailing slash is needed
    public static final String BASE_URL = "http://52.47.130.162/Project/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
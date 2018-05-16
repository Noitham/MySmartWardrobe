package com.soft.morales.mysmartwardrobe.model.persist;

public class ApiUtils {

    private ApiUtils() {}

    // Trailing slash is needed
    public static final String BASE_URL = "http://192.168.1.45:8000/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}

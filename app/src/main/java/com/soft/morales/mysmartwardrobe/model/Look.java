package com.soft.morales.mysmartwardrobe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Look {


    @SerializedName("garment_id")
    @Expose
    private List<Integer> garment_id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("date")
    @Expose
    private String date;

    public Look() {

    }

    public Look(List<Integer> garment_id, String username, String date) {
        this.garment_id = garment_id;
        this.username = username;
        this.date = date;
    }

    public List<Integer> getGarment_id() {
        return garment_id;
    }

    public void setGarment_id(List<Integer> garment_id) {
        this.garment_id = garment_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

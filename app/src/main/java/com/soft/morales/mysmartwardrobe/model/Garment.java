package com.soft.morales.mysmartwardrobe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Garment {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("photo")
    @Expose
    public String photo;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("season")
    @Expose
    public String season;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("size")
    @Expose
    public String size;
    @SerializedName("brand")
    @Expose
    public String brand;

    public Garment(String id, String name, String photo, String category, String season, String price, String color, String size, String brand) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.category = category;
        this.season = season;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
    }

    public Garment(String id, String name, String category, String season, String price, String color, String size, String brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.season = season;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
    }

    public Garment(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
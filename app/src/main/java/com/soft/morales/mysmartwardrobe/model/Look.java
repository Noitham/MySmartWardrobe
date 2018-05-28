package com.soft.morales.mysmartwardrobe.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Look {

    @SerializedName("torso")
    @Expose
    public String torso;
    @SerializedName("legs")
    @Expose
    public String legs;
    @SerializedName("feets")
    @Expose
    public String feets;

    public Look() {

    }

    public Look(String torso, String legs, String feets) {
        this.torso = torso;
        this.legs = legs;
        this.feets = feets;
    }

    public String getTorso() {
        return torso;
    }

    public void setTorso(String torso) {
        this.torso = torso;
    }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public String getFeets() {
        return feets;
    }

    public void setFeets(String feets) {
        this.feets = feets;
    }
}

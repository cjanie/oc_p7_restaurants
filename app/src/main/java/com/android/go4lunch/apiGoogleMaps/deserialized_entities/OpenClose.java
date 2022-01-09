package com.android.go4lunch.apiGoogleMaps.deserialized_entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpenClose {

    @SerializedName("day")
    @Expose
    private int day;

    @SerializedName("time")
    @Expose
    private String time;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

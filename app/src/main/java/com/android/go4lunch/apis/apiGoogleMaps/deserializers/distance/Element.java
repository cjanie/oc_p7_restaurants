package com.android.go4lunch.apis.apiGoogleMaps.deserializers.distance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("distance")
    @Expose
    private Distance distance;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}

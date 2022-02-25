package com.android.go4lunch.apis.apiGoogleMaps.deserializers.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsResponseRoot {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

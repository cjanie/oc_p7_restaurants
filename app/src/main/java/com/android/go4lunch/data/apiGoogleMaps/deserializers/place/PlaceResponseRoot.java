package com.android.go4lunch.data.apiGoogleMaps.deserializers.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceResponseRoot {
    @SerializedName("results")
    @Expose
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}



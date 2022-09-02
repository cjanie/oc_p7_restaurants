package com.android.go4lunch.data.apiGoogleMaps.deserializers.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponseRoot {

    @SerializedName("photos")
    @Expose
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}

package com.android.go4lunch.data.apiGoogleMaps.deserializers.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result{

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;

    @SerializedName("photos")
    @Expose
    private List<Photo> photos;

    @SerializedName("place_id")
    @Expose
    private String placeId;

    @SerializedName("formatted_address")
    @Expose
    private String address;

    @SerializedName("international_phone_number")
    @Expose
    private String phone;

    @SerializedName("website")
    @Expose
    private String website;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

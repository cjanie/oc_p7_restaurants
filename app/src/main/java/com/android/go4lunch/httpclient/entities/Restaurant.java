package com.android.go4lunch.httpclient.entities;

import java.time.LocalTime;
import java.util.Map;

public class Restaurant {

    private String id;

    private String name;

    private String address;

    private Map<Integer, Map<String, LocalTime>> planning;

    private String photoUrl;

    private Double latitude;

    private Double longitude;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Integer, Map<String, LocalTime>> getPlanning() {
        return planning;
    }

    public void setPlanning(Map<Integer, Map<String, LocalTime>> planning) {
        this.planning = planning;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}

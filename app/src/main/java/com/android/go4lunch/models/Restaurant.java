package com.android.go4lunch.models;

import java.time.LocalTime;
import java.util.Map;

public class Restaurant {

    private String name;

    private String address;

    private Map<Integer, Map <String, LocalTime>> planning;

    private String photoUrl;

    private Geolocation geolocation;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
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

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }
}

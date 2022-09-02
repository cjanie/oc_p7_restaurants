package com.android.go4lunch.businesslogic.entities;

import java.time.LocalTime;
import java.util.Map;

public class Restaurant {

    private String id;

    private String name;

    private String address;

    private Map<Integer, Map <String, LocalTime>> planning;

    private String photoUrl;

    private Geolocation geolocation;

    private String phone;

    private String webSite;

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Restaurant(String id, String name, String address) {
        this(name, address);
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}

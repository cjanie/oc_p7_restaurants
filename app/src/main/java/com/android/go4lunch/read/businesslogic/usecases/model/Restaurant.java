package com.android.go4lunch.read.businesslogic.usecases.model;

import java.time.LocalTime;

public class Restaurant {

    private String name;

    private String address;

    private LocalTime open;

    private LocalTime close;

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

    public LocalTime getOpen() {
        return open;
    }

    public void setOpen(LocalTime open) {
        this.open = open;
    }

    public LocalTime getClose() {
        return close;
    }

    public void setClose(LocalTime close) {
        this.close = close;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }
}

package com.android.go4lunch.read.businesslogic.usecases.model;


import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;

import java.time.LocalTime;

public class Restaurant {

    private String name;

    private CustomLocation location;

    private LocalTime open;

    private LocalTime close;

    public Restaurant(String name, CustomLocation location) {
        this.name = name;
        this.location = location;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomLocation getLocation() {
        return location;
    }

    public void setLocation(CustomLocation location) {
        this.location = location;
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
}

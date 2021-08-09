package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;

public class RestaurantVO {

    private String name;

    private CustomLocation location;

    private Info info;

    public RestaurantVO(String name, CustomLocation location, Info info) {
        this.name = name;
        this.location = location;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public CustomLocation getLocation() {
        return location;
    }

    public Info getInfo() {
        return info;
    }
}

package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;

public class RestaurantVO {

    private String name;

    private String address;

    private Info info;

    private Long distanceInfo;

    public RestaurantVO(String name, String address, Info info, Long distanceInfo) {
        this.name = name;
        this.address = address;
        this.info = info;
        this.distanceInfo = distanceInfo;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Info getInfo() {
        return this.info;
    }

    public Long getDistanceInfo() {
        return this.distanceInfo;
    }

}

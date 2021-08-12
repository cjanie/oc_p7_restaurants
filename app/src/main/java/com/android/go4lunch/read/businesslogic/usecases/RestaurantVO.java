package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

public class RestaurantVO {

    private Restaurant restaurant;

    private Info info;

    private Long distanceInfo;

    public RestaurantVO(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RestaurantVO(Restaurant restaurant, Info info) {
        this(restaurant);
        this.info = info;
    }


    public RestaurantVO(Restaurant restaurant, Info info, Long distanceInfo) {
        this(restaurant, info);
        this.distanceInfo = distanceInfo;
    }

    public RestaurantVO(Restaurant restaurant, Long distanceInfo) {
        this(restaurant);
        this.distanceInfo = distanceInfo;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Info getInfo() {
        return this.info;
    }

    public Long getDistanceInfo() {
        return this.distanceInfo;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public void setDistanceInfo(Long distanceInfo) {
        this.distanceInfo = distanceInfo;
    }
}

package com.android.go4lunch.read.businesslogic.usecases.model;

import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;

import java.time.LocalTime;

public class TimeInfo {

    private TimeProvider timeProvider;

    private RestaurantVO restaurant;

    public TimeInfo(TimeProvider timeProvider, RestaurantVO restaurant) {
        this.timeProvider = timeProvider;
        this.restaurant = restaurant;
    }

    public Info getInfo() {

        LocalTime open = this.restaurant.getRestaurant().getOpen();
        LocalTime close = this.restaurant.getRestaurant().getClose();
        LocalTime now = this.timeProvider.now();

        if(now.isAfter(close))
            return Info.CLOSE;
        if(!now.isBefore(close.minusHours(1)))
            return Info.CLOSING_SOON;
        if(now.isAfter(open))
            return Info.OPEN;
        if(!now.isBefore(open.minusHours(1)))
            return Info.OPENING_SOON;
        return Info.CLOSE;
    }

}

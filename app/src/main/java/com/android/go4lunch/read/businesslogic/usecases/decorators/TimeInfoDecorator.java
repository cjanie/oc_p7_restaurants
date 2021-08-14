package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.enums.TimeInfo;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;

import java.time.LocalTime;

public class TimeInfoDecorator implements Decorator {

    private TimeProvider timeProvider;

    private RestaurantVO restaurant;

    public TimeInfoDecorator(TimeProvider timeProvider, RestaurantVO restaurant) {
        this.timeProvider = timeProvider;
        this.restaurant = restaurant;
    }

    @Override
    public RestaurantVO decor() {
        this.restaurant.setTimeInfo(this.getInfo());
        return this.restaurant;
    }

    private TimeInfo getInfo() {

        LocalTime open = this.restaurant.getRestaurant().getOpen();
        LocalTime close = this.restaurant.getRestaurant().getClose();
        LocalTime now = this.timeProvider.now();

        if(now.isAfter(close))
            return TimeInfo.CLOSE;
        if(!now.isBefore(close.minusHours(1)))
            return TimeInfo.CLOSING_SOON;
        if(now.isAfter(open))
            return TimeInfo.OPEN;
        if(!now.isBefore(open.minusHours(1)))
            return TimeInfo.OPENING_SOON;
        return TimeInfo.CLOSE;
    }

}

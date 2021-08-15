package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.enums.TimeInfo;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;

import java.time.LocalTime;

public class TimeInfoDecorator implements Decorator<RestaurantVO> {

    private TimeProvider timeProvider;



    public TimeInfoDecorator(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public RestaurantVO decor(RestaurantVO restaurant) {
        restaurant.setTimeInfo(this.getInfo(restaurant));
        return restaurant;
    }

    private TimeInfo getInfo(RestaurantVO restaurant) {

        LocalTime open = restaurant.getRestaurant().getOpen();
        LocalTime close = restaurant.getRestaurant().getClose();
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

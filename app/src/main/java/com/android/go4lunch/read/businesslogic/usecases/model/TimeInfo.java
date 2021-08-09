package com.android.go4lunch.read.businesslogic.usecases.model;

import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.Info;

import java.time.LocalTime;

public class TimeInfo {

    private TimeProvider timeProvider;

    public TimeInfo(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public Info handle(Restaurant restaurant) {
        return this.getInfo(restaurant);
    }

    private Info getInfo(Restaurant restaurant) {
        LocalTime open = restaurant.getOpen();
        LocalTime close = restaurant.getClose();
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

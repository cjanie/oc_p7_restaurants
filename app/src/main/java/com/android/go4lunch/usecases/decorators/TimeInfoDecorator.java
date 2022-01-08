package com.android.go4lunch.usecases.decorators;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;

import java.time.LocalTime;
import java.util.Map;

public class TimeInfoDecorator {

    private TimeProvider timeProvider;

    private DateProvider dateProvider;

    public TimeInfoDecorator(TimeProvider timeProvider, DateProvider dateProvider) {
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
    }

    public RestaurantVO decor(RestaurantVO restaurant) {
        restaurant.setTimeInfo(this.getInfo(restaurant, this.dateProvider.today()));
        return restaurant;
    }

    private TimeInfo getInfo(RestaurantVO restaurant, int dayOfToday) {
        Map<Integer, Map<String, LocalTime>> planning = restaurant.getRestaurant().getPlanning();
        if(planning == null)
            return TimeInfo.DEFAULT_TIME_INFO;

        LocalTime now = this.timeProvider.now();

        if(planning.get(dayOfToday) != null) {
            if(planning.get(dayOfToday).get("close").isBefore(now))
                return TimeInfo.CLOSE;
            if(planning.get(dayOfToday).get("open").isBefore(now)) {
                if(planning.get(dayOfToday).get("close").isAfter(now.plusHours(1)))
                    return TimeInfo.OPEN;
                return TimeInfo.CLOSING_SOON;
            }

        }






        /*
        if(openingDays == null || openingDays.isEmpty() || open == null || close == null)
            return TimeInfo.DEFAULT_TIME_INFO;

        if(openingDays.contains(dayOfToday)) {
            if(now.isAfter(close))
                return TimeInfo.CLOSE;
            if(!now.isBefore(close.minusHours(1)))
                return TimeInfo.CLOSING_SOON;
            if(now.isAfter(open))
                return TimeInfo.OPEN;
            if(!now.isBefore(open.minusHours(1)))
                return TimeInfo.OPENING_SOON;
        }

        if(openingDays.contains(dayOfToday + 1)) {
            if(!now.isBefore(open.minusHours(1)))
                return TimeInfo.OPENING_SOON;
        }

         */

        return TimeInfo.CLOSE;
    }

}

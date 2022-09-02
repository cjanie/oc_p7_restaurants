package com.android.go4lunch.businesslogic.decorators;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.enums.TimeInfo;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TimeInfoDecorator {

    private TimeProvider timeProvider;

    private DateProvider dateProvider;

    public TimeInfoDecorator(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    public TimeInfoDecorator(TimeProvider timeProvider, DateProvider dateProvider) {
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
    }

    public TimeInfo decor(Restaurant restaurant) {
        return this.createTimeInfo(restaurant);
    }

    private TimeInfo createTimeInfo(Restaurant restaurant) {

        if(restaurant.getPlanning() == null)
            return TimeInfo.DEFAULT_TIME_INFO;

        LocalTime now = this.timeProvider.now();

        Map<String, LocalTime> openHoursToday = this.getOpenHoursToday(restaurant);
        if(!openHoursToday.isEmpty()) {
            if(openHoursToday.get("close").isBefore(now))
                return TimeInfo.CLOSE;
            if(openHoursToday.get("open").isBefore(now)) {
                if(openHoursToday.get("close").isAfter(now.plusHours(1)))
                    return TimeInfo.OPEN;
                return TimeInfo.CLOSING_SOON;
            }

        }

        return TimeInfo.CLOSE;
    }

    public Map<String, LocalTime> getOpenHoursToday(Restaurant restaurant) {
        Map<String, LocalTime> openHoursToday = new HashMap();
        if(restaurant.getPlanning() != null) {

            Integer today = this.dateProvider.today();

            if(restaurant.getPlanning().get(today) != null)
                openHoursToday = restaurant.getPlanning().get(today);
        }
        return openHoursToday;
    }

}

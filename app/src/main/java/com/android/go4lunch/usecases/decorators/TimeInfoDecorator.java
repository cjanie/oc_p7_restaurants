package com.android.go4lunch.usecases.decorators;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.usecases.models.RestaurantModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class TimeInfoDecorator {

    private TimeProvider timeProvider;

    private DateProvider dateProvider;

    public TimeInfoDecorator(TimeProvider timeProvider, DateProvider dateProvider) {
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
    }

    public TimeInfo decor(Restaurant restaurant) {
        return this.getTimeInfo(restaurant);
    }

    private TimeInfo getTimeInfo(Restaurant restaurant) {
        Map<Integer, Map<String, LocalTime>> planning = restaurant.getPlanning();
        if(planning == null)
            return TimeInfo.DEFAULT_TIME_INFO;

        LocalTime now = this.timeProvider.now();
        int today = this.dateProvider.today();
        if(planning.get(today) != null) {
            if(planning.get(today).get("close").isBefore(now))
                return TimeInfo.CLOSE;
            if(planning.get(today).get("open").isBefore(now)) {
                if(planning.get(today).get("close").isAfter(now.plusHours(1)))
                    return TimeInfo.OPEN;
                return TimeInfo.CLOSING_SOON;
            }

        }

        return TimeInfo.CLOSE;
    }

}

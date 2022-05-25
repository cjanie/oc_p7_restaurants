package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.decorators.TimeInfoDecorator;
import com.android.go4lunch.businesslogic.enums.TimeInfo;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.time.LocalTime;
import java.util.List;

public class RestaurantModel {

    private Restaurant restaurant;

    private TimeProvider timeProvider;

    private DateProvider dateProvider;

    private List<String> visitorsId;

    private Long distance;

    public RestaurantModel(
            Restaurant restaurant,
            TimeProvider timeProvider,
            DateProvider dateProvider,
            Long distance,
            List<String> visitorsId
    ) {
        this.restaurant = restaurant;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
        this.distance = distance;
        this.visitorsId = visitorsId;

    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }


    public TimeInfo getTimeInfo() {
        TimeInfoDecorator decorator = new TimeInfoDecorator(this.timeProvider, this.dateProvider);
        return decorator.decor(this.restaurant);
    }

    public LocalTime getCloseToday() {
        return this.restaurant.getPlanning().get(this.dateProvider.today()).get("close");
    }

    public Long getDistance() {
        return this.distance;
    }



    public int getVisitorsCount() {
        return this.visitorsId.size();
    }

}

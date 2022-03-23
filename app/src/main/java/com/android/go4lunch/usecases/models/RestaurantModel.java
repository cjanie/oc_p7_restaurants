package com.android.go4lunch.usecases.models;

import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.usecases.enums.TimeInfo;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.enums.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantModel {

    private Restaurant restaurant;

    private TimeProvider timeProvider;

    private DateProvider dateProvider;

    private List<Workmate> visitors;

    private Long distance;

    public RestaurantModel(
            Restaurant restaurant,
            TimeProvider timeProvider,
            DateProvider dateProvider,
            Long distance,
            List<Workmate> visitors
    ) {
        this.restaurant = restaurant;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
        this.distance = distance;
        this.visitors = visitors;

    }

/*
    public RestaurantModel(Restaurant restaurant, TimeInfo timeInfo, Long distanceInfo) {
        this(restaurant, timeInfo);
        this.distanceInfo = distanceInfo;
    }

    public RestaurantModel(Restaurant restaurant, Long distanceInfo) {
        this(restaurant);
        this.distanceInfo = distanceInfo;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
*/
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
        return this.visitors.size();
    }

}

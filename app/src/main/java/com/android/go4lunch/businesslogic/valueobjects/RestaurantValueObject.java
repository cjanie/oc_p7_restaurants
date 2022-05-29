package com.android.go4lunch.businesslogic.valueobjects;

import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.decorators.TimeInfoDecorator;
import com.android.go4lunch.businesslogic.enums.TimeInfo;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantValueObject {

    private Restaurant restaurant;

    private TimeInfo timeInfo;

    private LocalTime closingTimeToday;

    private int visitorsCount;

    private int likesCount;

    private Long distance;



    public RestaurantValueObject(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.visitorsCount = 0;
        this.likesCount = 0;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }
/*
    public void setTimeInfo(TimeProvider timeProvider, DateProvider dateProvider) {
        TimeInfoDecorator decorator = new TimeInfoDecorator(timeProvider, dateProvider);
        this.timeInfo = decorator.decor(this.restaurant);
    }
*/

    public TimeInfo getTimeInfo() {
        return this.timeInfo;
    }

    public void setClosingTimeToday(DateProvider dateProvider) {
        this.closingTimeToday = this.restaurant.getPlanning().get(dateProvider.today()).get("close");
    }

    public LocalTime getClosingTimeToday() {
        return this.closingTimeToday;
    }

    public int getVisitorsCount() {
        return this.visitorsCount;
    }

    public void setVisitorsCount(int visitorsCount) {
        this.visitorsCount = visitorsCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getNumberOfStarts() {
        if(this.likesCount < 4) {
            return this.likesCount;
        }
        return 3;
    }

    public Long getDistance() {
        return this.distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}

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
/*
    private List<String> visitorsId;

    private Long distance;

 */

    public RestaurantValueObject(Restaurant restaurant) {
        this.restaurant = restaurant;
        //this.visitorsId = new ArrayList<>();
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
/*
    public Long getDistance() {
        return this.distance;
    }



    public int getVisitorsCount() {
        return this.visitorsId.size();
    }

 */

}

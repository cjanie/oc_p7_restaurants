package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.TimeInfo;

import java.util.ArrayList;
import java.util.List;

public class RetrieveRestaurants {

    private RestaurantQuery restaurantQuery;


    public RetrieveRestaurants(RestaurantQuery restaurantQuery) {
        this.restaurantQuery = restaurantQuery;
    }

    public List<Restaurant> handle() {
        return this.restaurantQuery.findAll();
    }

    public List<RestaurantVO> handleVO(TimeProvider timeProvider) {
        List<RestaurantVO> restaurantVOs = new ArrayList<>();
        if(!this.handle().isEmpty()) {
            for(Restaurant r: this.handle()) {
                RestaurantVO restaurantVO = new RestaurantVO(
                        r.getName(),
                        r.getLocation(),
                        new TimeInfo(timeProvider).handle(r)
                );
                restaurantVOs.add(restaurantVO);
            }
        }
        return restaurantVOs;
    }
}

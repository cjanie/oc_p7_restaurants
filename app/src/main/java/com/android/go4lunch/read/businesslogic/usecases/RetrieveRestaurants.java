package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RetrieveRestaurants {

    private RestaurantQuery restaurantQuery;



    public RetrieveRestaurants(RestaurantQuery restaurantQuery) {
        this.restaurantQuery = restaurantQuery;
    }

    public List<RestaurantVO> handle() {
        List<RestaurantVO> restaurantVOs = new ArrayList<>();
        if(!this.findAll().isEmpty()) {
            for(Restaurant r: this.findAll()) {
                RestaurantVO restaurantVO = new RestaurantVO(r);
                restaurantVOs.add(restaurantVO);
            }
        }
        return restaurantVOs;
    }

    private List<Restaurant> findAll() {
        return this.restaurantQuery.findAll();
    }


}

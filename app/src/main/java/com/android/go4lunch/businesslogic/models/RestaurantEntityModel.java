package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;

import java.util.ArrayList;
import java.util.List;

public class RestaurantEntityModel {

    public Restaurant findWorkmateSelection(String workmateId, List<Selection> selections) {
        if(!selections.isEmpty()) {
            for(Selection selection: selections) {
                if(selection.getWorkmateId().equals(workmateId)) {
                    Restaurant selectedRestaurant = new Restaurant(selection.getRestaurantName());
                    selectedRestaurant.setId(selection.getRestaurantId());
                    return selectedRestaurant;
                }
            }
        }
        return null;
    }

    public List<RestaurantValueObject> formatRestaurantsToValueObjects(List<Restaurant> restaurants) {
        List<Restaurant> filteredRestaurantsWithGeolocation = this.filterRestaurantsWithGeolocation(restaurants);
        List<RestaurantValueObject> restaurantVOs = new ArrayList<>();
        if(!filteredRestaurantsWithGeolocation.isEmpty()) {
            for(Restaurant restaurant: restaurants) {
                RestaurantValueObject restaurantVO = new RestaurantValueObject(restaurant);
                restaurantVOs.add(restaurantVO);
            }
        }
        return restaurantVOs;
    }

    private List<Restaurant> filterRestaurantsWithGeolocation(List<Restaurant> restaurants) {
        List<Restaurant> filteredRestaurantsWithGeolocation = new ArrayList<>();
        for(Restaurant restaurant: restaurants) {
            if(restaurant.getGeolocation() != null) {
                filteredRestaurantsWithGeolocation.add(restaurant);
            }
        }
        return filteredRestaurantsWithGeolocation;
    }
}

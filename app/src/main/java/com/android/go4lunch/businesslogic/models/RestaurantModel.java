package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantModel {

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

    public Observable<List<RestaurantValueObject>> formatRestaurantsAsValueObjects(
            Observable<List<Restaurant>> restaurantsObservable
    ) {
        return restaurantsObservable
                .map(restaurants ->
                        this.formatRestaurantsToValueObjects(restaurants));
    }
    private List<RestaurantValueObject> formatRestaurantsToValueObjects(List<Restaurant> restaurants) {
        // All VOs have a Geolocation in order to update distance
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

    public Observable<List<RestaurantValueObject>> updateRestaurantsWithVisitorsCount(
            List<RestaurantValueObject> restaurantVOs,
            Observable<List<Selection>> selectionsObservable
    ) {
        return selectionsObservable.map(selections -> {
            List<RestaurantValueObject> restaurantVOsCopy = restaurantVOs;
            if(!restaurantVOsCopy.isEmpty()) {
                for(RestaurantValueObject restaurantVO: restaurantVOsCopy) {
                    int visitorsCount = this.getVisitorsCountByRestaurantId(selections, restaurantVO.getRestaurant().getId());
                    restaurantVO.setVisitorsCount(visitorsCount);
                }
            }
            return restaurantVOsCopy;
        });
    }

    private int getVisitorsCountByRestaurantId(List<Selection> selections, String restaurantId) {
        int count = 0;
        if (!selections.isEmpty()) {
            for (Selection selection : selections) {
                if (selection.getRestaurantId().equals(restaurantId)) {
                    count += 1;
                }
            }
        }
        return count;
    }
}

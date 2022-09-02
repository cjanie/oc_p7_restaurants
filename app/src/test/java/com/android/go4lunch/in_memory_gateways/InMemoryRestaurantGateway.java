package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryRestaurantGateway implements RestaurantGateway {

    private List<Restaurant> restaurants;

    public InMemoryRestaurantGateway() {
        this.restaurants = new ArrayList<>();
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {
        return Observable.just(this.restaurants);
    }

    @Override
    public Observable<Restaurant> getRestaurantById(String restaurantId) {
        return null;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}

package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryRestaurantGateway implements RestaurantGateway {

    private List<Restaurant> restaurants;

    public InMemoryRestaurantGateway() {
        this.restaurants = new ArrayList<>();
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Geolocation myPosition, int radius) {
        // TODO
        return null;
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearbyWithDetails(Geolocation myPosition, int radius) {
        return Observable.just(this.restaurants);
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby() {
        return Observable.just(this.restaurants);
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}

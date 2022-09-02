package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.util.List;

import io.reactivex.Observable;

public interface RestaurantGateway {

    Observable<List<Restaurant>> getRestaurantsNearby(
            Double myLatitude,
            Double myLongitude,
            int radius
    );

    Observable<Restaurant> getRestaurantById(String restaurantId);
}

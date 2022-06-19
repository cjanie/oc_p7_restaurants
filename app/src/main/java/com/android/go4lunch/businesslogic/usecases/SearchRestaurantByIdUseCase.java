package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;

import io.reactivex.Observable;

public class SearchRestaurantByIdUseCase {

    private RestaurantGateway restaurantGateway;

    public SearchRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<Restaurant> handle(String restaurantId) {
        return this.restaurantGateway.getRestaurantById(restaurantId);
    }
}

package com.android.go4lunch.usecases;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;

import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCase {

    private final RestaurantGateway restaurantGateway;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway
    ) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<List<Restaurant>> handle(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius);
    }


}

package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantGateway;

import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCase {

    private final RestaurantGateway restaurantGateway;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway
    ) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<List<Restaurant>> handle(Geolocation myPosition, int radius) {
        return this.restaurantGateway.getRestaurantsNearbyWithDetails(myPosition, radius);
    }


}

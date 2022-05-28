package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.models.RestaurantEntityModel;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCase {

    private final RestaurantGateway restaurantGateway;

    private final RestaurantEntityModel restaurantEntityModel;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway
    ) {
        this.restaurantGateway = restaurantGateway;

        this.restaurantEntityModel = new RestaurantEntityModel();
    }

    public Observable<List<RestaurantValueObject>> handle(Double myLatitude, Double myLongitude, int radius) {
        return this.getFormattedRestaurants(myLatitude, myLongitude, radius);
    }


    // Get Data from gateways
    private Observable<List<Restaurant>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius);
    }

    // Transformations

    public Observable<List<RestaurantValueObject>> getFormattedRestaurants(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurants(myLatitude, myLongitude, radius)
                .map(restaurants -> this.restaurantEntityModel.formatRestaurantsToValueObjects(restaurants));
    }



}

package com.android.go4lunch.data.gateways_impl;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class RestaurantGatewayInMemoryImpl implements RestaurantGateway {

    private Double latitude;

    private Double longitude;

    private int radius;

    private RestaurantGatewayImpl restaurantGatewayFirstRequest;

    private BehaviorSubject<List<Restaurant>> restaurantsSubject;

    public RestaurantGatewayInMemoryImpl(
            RestaurantGatewayImpl restaurantGatewayFirstRequest
    ) {
        this.restaurantGatewayFirstRequest = restaurantGatewayFirstRequest;
        this.restaurantsSubject = BehaviorSubject.create();
    }

    public void updateSubject(
            Double latitude,
            Double longitude,
            int radius
    ) {
        restaurantGatewayFirstRequest.getRestaurantsNearby(
                latitude,
                longitude,
                radius
        ).subscribe(restaurants -> {
            this.restaurantsSubject.onNext(restaurants);
        });

    };

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {

        return this.restaurantsSubject;
    }
}

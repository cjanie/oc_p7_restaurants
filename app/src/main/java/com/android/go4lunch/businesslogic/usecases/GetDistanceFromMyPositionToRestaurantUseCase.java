package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;

import io.reactivex.Observable;



public class GetDistanceFromMyPositionToRestaurantUseCase {

    private DistanceGateway distanceGateway;

    public GetDistanceFromMyPositionToRestaurantUseCase(DistanceGateway distanceGateway) {
        this.distanceGateway = distanceGateway;
    }

    public Observable<Long> handle(Geolocation myPosition, Geolocation restaurantLocation) {
        return this.distanceGateway.getDistanceInMeter(myPosition, restaurantLocation);
    }
}

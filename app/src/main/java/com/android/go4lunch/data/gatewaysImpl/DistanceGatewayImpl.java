package com.android.go4lunch.data.gatewaysImpl;

import com.android.go4lunch.data.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.entities.Geolocation;

import io.reactivex.Observable;

public class DistanceGatewayImpl implements DistanceGateway {

    private DistanceRepository distanceRepository;

    public DistanceGatewayImpl(DistanceRepository distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    @Override
    public Observable<Long> getDistanceInMeter(Geolocation myPosition, Geolocation restaurantGeolocation) {
        String destination = restaurantGeolocation.getLatitude().toString() + "," + restaurantGeolocation.getLongitude().toString();
        String origin = myPosition.getLatitude().toString() + "," + restaurantGeolocation.getLongitude().toString();
        return this.distanceRepository.getDistanceInMeter(destination, origin)

                .map(distance ->
                        Long.valueOf(distance));
    }
}

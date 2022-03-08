package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.gateways.DistanceGateway;
import com.android.go4lunch.models.Geolocation;

import io.reactivex.Observable;

public class DistanceGatewayImpl implements DistanceGateway {

    private DistanceRepository distanceRepository;

    public DistanceGatewayImpl(DistanceRepository distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    @Override
    public Observable<Long> getDistanceInMeter(Geolocation myPosition, Geolocation restaurantGeolocation) throws NullDistanceResponseException {
        String destination = restaurantGeolocation.getLatitude().toString() + "," + restaurantGeolocation.getLongitude().toString();
        String origin = myPosition.getLatitude().toString() + "," + restaurantGeolocation.getLongitude().toString();
        return this.distanceRepository.getDistanceInMeter(destination, origin)
                .map(distance ->
                        Long.valueOf(distance));
    }
}

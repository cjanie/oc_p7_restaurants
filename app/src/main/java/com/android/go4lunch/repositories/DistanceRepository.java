package com.android.go4lunch.repositories;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.apiGoogleMaps.DistanceStream;
import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.models.Geolocation;

import io.reactivex.Observable;

public class DistanceRepository implements DistanceQuery {

    @Override
    public Observable<Long> getDistanceInMeter(Geolocation myPosition, Geolocation restaurantGeolocation) throws NullDistanceResponseException {
        String destination = restaurantGeolocation.getLatitude().toString() + "," + restaurantGeolocation.getLongitude().toString();
        String origin = myPosition.getLatitude().toString() + "," + restaurantGeolocation.getLongitude().toString();
        return DistanceStream.getDistanceInMeter(destination, origin)
                .map(distance ->
                        Long.valueOf(distance));
    }
}

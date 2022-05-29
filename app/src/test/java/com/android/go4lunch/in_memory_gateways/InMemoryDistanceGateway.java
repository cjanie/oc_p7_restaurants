package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.entities.Geolocation;

import io.reactivex.Observable;


public class InMemoryDistanceGateway implements DistanceGateway {

    private Observable<Long> distance;

    public InMemoryDistanceGateway(Observable<Long> distance) {
        this.distance = distance;
    }


    @Override
    public Observable<Long> getDistanceInMeter(Geolocation myPosition, Geolocation restaurantGeolocation) {
        return this.distance;
    }

}

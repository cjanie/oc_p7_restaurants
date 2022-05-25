package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.entities.Geolocation;

import io.reactivex.Observable;


public class InMemoryDistanceRepository implements DistanceGateway {

    private Observable<Long> distance;

    public InMemoryDistanceRepository(Observable<Long> distance) {
        this.distance = distance;
    }


    @Override
    public Observable<Long> getDistanceInMeter(Geolocation myPosition, Geolocation restaurantGeolocation) throws NullDistanceResponseException {
        if(this.distance == null)
            throw new NullDistanceResponseException();
        return this.distance;
    }
}

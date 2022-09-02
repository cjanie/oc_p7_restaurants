package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.businesslogic.entities.Geolocation;

import io.reactivex.Observable;

public interface DistanceGateway {

    Observable<Long> getDistanceInMeter(
            Geolocation myPosition,
            Geolocation restaurantGeolocation
    );
}

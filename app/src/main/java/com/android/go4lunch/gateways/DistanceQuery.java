package com.android.go4lunch.gateways;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Selection;

import io.reactivex.Observable;

public interface DistanceQuery {

    Observable<Long> getDistanceInMeter(
            Geolocation myPosition,
            Geolocation restaurantGeolocation
    ) throws NullDistanceResponseException;
}

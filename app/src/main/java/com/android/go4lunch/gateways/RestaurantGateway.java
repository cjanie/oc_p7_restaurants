package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import java.util.List;

import io.reactivex.Observable;

public interface RestaurantGateway {

    Observable<List<Restaurant>> getRestaurantsNearby(
            Double myLatitude,
            Double myLongitude,
            int radius
    );

}

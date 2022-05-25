package com.android.go4lunch.businesslogic.decorators;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.models.RestaurantModel;
import com.android.go4lunch.businesslogic.entities.Geolocation;

import io.reactivex.Observable;

public class DistanceInfoDecorator {

    private DistanceGateway distanceGateway;

    public DistanceInfoDecorator(DistanceGateway distanceGateway) {
        this.distanceGateway = distanceGateway;
    }

    public Observable<RestaurantModel> decor(Geolocation myPosition, RestaurantModel restaurant) throws NullDistanceResponseException {
        if(restaurant.getRestaurant().getGeolocation() != null) {
            return this.getDistanceFromMyPosition(myPosition, restaurant).map(distance -> {
                if(distance != null) {
                    //restaurant.setDistanceInfo(distance);
                }
                return restaurant;
            });

        }
        return Observable.just(restaurant);
    }

    private Observable<Long> getDistanceFromMyPosition(Geolocation myPosition, RestaurantModel restaurant) throws NullDistanceResponseException {
        return this.distanceGateway.getDistanceInMeter(myPosition, restaurant.getRestaurant().getGeolocation());
    }

}

package com.android.go4lunch.businesslogic.decorators;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.businesslogic.entities.Geolocation;

import io.reactivex.Observable;

public class DistanceInfoDecorator {

    private DistanceGateway distanceGateway;

    public DistanceInfoDecorator(DistanceGateway distanceGateway) {
        this.distanceGateway = distanceGateway;
    }

    public Observable<RestaurantValueObject> decor(Geolocation myPosition, RestaurantValueObject restaurant) throws NullDistanceResponseException {
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

    private Observable<Long> getDistanceFromMyPosition(Geolocation myPosition, RestaurantValueObject restaurant) throws NullDistanceResponseException {
        return this.distanceGateway.getDistanceInMeter(myPosition, restaurant.getRestaurant().getGeolocation());
    }

}

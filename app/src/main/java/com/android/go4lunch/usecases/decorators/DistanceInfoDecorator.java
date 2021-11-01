package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Geolocation;

import io.reactivex.Observable;

public class DistanceInfoDecorator {

    private DistanceQuery distanceQuery;

    public DistanceInfoDecorator(DistanceQuery distanceQuery) {
        this.distanceQuery = distanceQuery;
    }

    public Observable<RestaurantVO> decor(Geolocation myPosition, RestaurantVO restaurant) throws NullDistanceResponseException {
        if(restaurant.getRestaurant().getGeolocation() != null) {
            return this.getDistanceFromMyPosition(myPosition, restaurant).map(distance -> {
                if(distance != null) {
                    restaurant.setDistanceInfo(distance);
                }
                return restaurant;
            });

        }
        return Observable.just(restaurant);
    }

    private Observable<Long> getDistanceFromMyPosition(Geolocation myPosition, RestaurantVO restaurant) throws NullDistanceResponseException {
        return this.distanceQuery.getDistanceInMeter(myPosition, restaurant.getRestaurant().getGeolocation());
    }

}

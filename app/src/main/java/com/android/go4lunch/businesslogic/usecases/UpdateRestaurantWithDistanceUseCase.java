package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import io.reactivex.Observable;

public class UpdateRestaurantWithDistanceUseCase {

    private DistanceGateway distanceGateway;

    public UpdateRestaurantWithDistanceUseCase(DistanceGateway distanceGateway) {
        this.distanceGateway = distanceGateway;
    }

    public Observable<RestaurantValueObject> handle(RestaurantValueObject restaurantVO, Double myLatitude, Double myLongitude) {
        return this.updateRestaurantWithDistance(restaurantVO, myLatitude, myLongitude);
    }

    private Observable<RestaurantValueObject> updateRestaurantWithDistance(RestaurantValueObject restaurantVO, Double myLatitude, Double myLongitude) {
        Geolocation myPosition = new Geolocation(myLatitude, myLongitude);
        try {
            return this.distanceGateway.getDistanceInMeter(myPosition, restaurantVO.getRestaurant().getGeolocation())
                    .map(distance -> {
                        RestaurantValueObject restaurantVOCopy = restaurantVO;
                        restaurantVOCopy.setDistance(distance);
                        return restaurantVOCopy;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

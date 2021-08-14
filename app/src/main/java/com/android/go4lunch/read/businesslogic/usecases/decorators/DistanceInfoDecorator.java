package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;
import com.android.go4lunch.read.businesslogic.gateways.WithLocationPermissionDecorator;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

public class DistanceInfoDecorator implements WithLocationPermissionDecorator {

    private GeolocationProvider geolocationProvider;

    private RestaurantVO restaurantVO;

    public DistanceInfoDecorator(RestaurantVO restaurantVO) {
        this.restaurantVO = restaurantVO;
    }

    @Override
    public RestaurantVO decor(Geolocation myPosition) {
        this.restaurantVO.setDistanceInfo(this.getDistanceFromMyPosition(myPosition));
        return this.restaurantVO;
    }

    private long getDistanceFromMyPosition(Geolocation myPosition) {
        return this.getSquareDistanceRoundDown(myPosition, this.restaurantVO.getRestaurant().getGeolocation());
    }


    private Long getSquareDistanceRoundDown(Geolocation a, Geolocation b) {
        Long roundDown = (long) Math.floor(this.getSquareDistance(a, b));
        return roundDown;
    }

    private Double getSquareDistance(Geolocation a, Geolocation b) {
        Double squareDistance = 0D;
        Double distanceOnAxisX;
        Double distanceOnAxisY;

        if(a != null && b != null) {
            if(a.getX() < b.getX())
                distanceOnAxisX = b.getX() - a.getX();
            else distanceOnAxisX = a.getX() - b.getX();

            if(a.getY() < b.getY())
                distanceOnAxisY = b.getY() - a.getY();
            else distanceOnAxisY = a.getY() - b.getY();
            squareDistance = distanceOnAxisX + distanceOnAxisY;
        }

        return squareDistance;
    }

}

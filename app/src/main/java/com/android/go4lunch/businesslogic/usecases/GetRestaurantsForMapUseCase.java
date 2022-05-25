package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForMapUseCase {

    private RestaurantGateway restaurantGateway;

    public GetRestaurantsForMapUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<List<MarkerOptions>> getRestaurantsMarkers(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius).map(restaurants -> {
            List<MarkerOptions> markersOptions = new ArrayList<>();
            if(!restaurants.isEmpty()) {
                for(Restaurant restaurant: restaurants) {
                    if(restaurant.getGeolocation() != null) {
                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(
                                restaurant.getGeolocation().getLatitude(),
                                restaurant.getGeolocation().getLongitude())
                        ).title(restaurant.getName());
                        markersOptions.add(markerOptions);
                    }
                }
            }
            return markersOptions;
        });
    }

}

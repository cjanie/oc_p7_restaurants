package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantQuery;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForMap {

    private RestaurantQuery restaurantQuery;

    public GetRestaurantsForMap(RestaurantQuery restaurantQuery) {
        this.restaurantQuery = restaurantQuery;
    }

    public Observable<List<MarkerOptions>> getRestaurantsMarkers(Geolocation myPosition, int radius) {
        return this.restaurantQuery.getRestaurantsNearbyWithDetails(myPosition, radius).map(restaurants -> {
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

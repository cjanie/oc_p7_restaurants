package com.android.go4lunch.repositories;

import com.android.go4lunch.gateways.RestaurantQuery;
import com.android.go4lunch.httpclient.RestaurantStream;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantRepository implements RestaurantQuery {

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Geolocation myPosition, int radius) {
        return RestaurantStream.getRestaurantsNearby(myPosition.getLatitude(), myPosition.getLongitude(), radius)
                .flatMap(restaurants -> this.formatRestaurants(restaurants));
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearbyWithDetails(Geolocation myPosition, int radius) {
        return RestaurantStream.getRestaurantsNearbyWithDetails(myPosition.getLatitude(), myPosition.getLongitude(), radius)
                .flatMap(restaurants -> this.formatRestaurants(restaurants));
    }

    private Observable<List<Restaurant>> formatRestaurants(List<com.android.go4lunch.httpclient.entities.Restaurant> listToFormat) {
        List<Restaurant> formattedList = new ArrayList<>();
        if(!listToFormat.isEmpty()) {
            for(com.android.go4lunch.httpclient.entities.Restaurant r: listToFormat) {
                formattedList.add(this.formatRestaurant(r));
            }
        }
        return Observable.just(formattedList);
    }

    private Restaurant formatRestaurant(com.android.go4lunch.httpclient.entities.Restaurant r) {
        Restaurant restaurant = null;
        if(r != null) {
            restaurant = new Restaurant(r.getName(), r.getAddress());
            restaurant.setGeolocation(new Geolocation(
                    r.getLatitude(),
                    r.getLongitude()
            ));
            restaurant.setPlanning(r.getPlanning());
            restaurant.setPhotoUrl(r.getPhotoUrl());
        }
        return restaurant;
    }

}

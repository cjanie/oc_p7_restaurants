package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantGatewayImpl implements RestaurantGateway {

    private RestaurantRepository restaurantRepository;

    private Observable<List<Restaurant>> restaurantsObservable;

    public RestaurantGatewayImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {
        if(this.restaurantsObservable == null) {
            this.restaurantsObservable = Observable.just(new Mock().restaurants());
/*
            this.restaurantsObservable = this.restaurantRepository.getRestaurantsNearbyWithDetails(myLatitude, myLongitude, radius)
                    .flatMap(restaurants -> this.formatRestaurants(restaurants));
*/
        }

        return this.restaurantsObservable;
    }

    private Observable<List<Restaurant>> formatRestaurants(List<com.android.go4lunch.apis.apiGoogleMaps.entities.Restaurant> listToFormat) {
        List<Restaurant> formattedList = new ArrayList<>();
        if(!listToFormat.isEmpty()) {
            for(com.android.go4lunch.apis.apiGoogleMaps.entities.Restaurant r: listToFormat) {
                formattedList.add(this.formatRestaurant(r));
            }
        }
        return Observable.just(formattedList);
    }

    private Restaurant formatRestaurant(com.android.go4lunch.apis.apiGoogleMaps.entities.Restaurant r) {
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

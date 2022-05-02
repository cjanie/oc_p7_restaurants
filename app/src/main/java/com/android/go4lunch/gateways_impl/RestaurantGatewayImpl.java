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

    public RestaurantGatewayImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Geolocation myPosition, int radius) {
        return Observable.just(new Mock().restaurants());
/*
        return this.restaurantRepository.getRestaurantsNearby(myPosition.getLatitude(), myPosition.getLongitude(), radius)
                .flatMap(restaurants -> this.formatRestaurants(restaurants));

*/
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearbyWithDetails(Geolocation myPosition, int radius) {
        return Observable.just(new Mock().restaurants());
/*
        return this.restaurantRepository.getRestaurantsNearbyWithDetails(myPosition.getLatitude(), myPosition.getLongitude(), radius)
                .flatMap(restaurants -> this.formatRestaurants(restaurants));

*/
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby() {
        return Observable.just(new Mock().restaurants());
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

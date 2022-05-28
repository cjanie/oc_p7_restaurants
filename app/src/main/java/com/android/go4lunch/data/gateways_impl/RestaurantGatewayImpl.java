package com.android.go4lunch.data.gateways_impl;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.data.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantGatewayImpl implements RestaurantGateway {

    private RestaurantRepository restaurantRepository;

    private Observable<List<Restaurant>> restaurantsObservable;

    public RestaurantGatewayImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {
        if(this.restaurantsObservable == null) {
            //this.restaurantsObservable = Observable.just(new Mock().restaurants()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            this.restaurantsObservable = this.restaurantRepository.getRestaurantsNearbyWithDetails(myLatitude, myLongitude, radius)
                    .flatMap(restaurants -> this.formatRestaurants(restaurants));

        }

        return this.restaurantsObservable;
    }

    private Observable<List<Restaurant>> formatRestaurants(List<com.android.go4lunch.data.apiGoogleMaps.entities.Restaurant> listToFormat) {
        List<Restaurant> formattedList = new ArrayList<>();
        if(!listToFormat.isEmpty()) {
            for(com.android.go4lunch.data.apiGoogleMaps.entities.Restaurant r: listToFormat) {
                formattedList.add(this.formatRestaurant(r));
            }
        }
        return Observable.just(formattedList);
    }

    private Restaurant formatRestaurant(com.android.go4lunch.data.apiGoogleMaps.entities.Restaurant r) {
        Restaurant restaurant = null;
        if(r != null) {
            restaurant = new Restaurant(r.getName(), r.getAddress());
            restaurant.setId(r.getId());
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

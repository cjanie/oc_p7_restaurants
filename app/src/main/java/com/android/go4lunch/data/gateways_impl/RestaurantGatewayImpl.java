package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.data.apiGoogleMaps.entities.RestaurantDTO;
import com.android.go4lunch.data.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class RestaurantGatewayImpl implements RestaurantGateway {

    private String TAG = "RESTAURANT GATEWAY IMPL";

    private RestaurantRepository restaurantRepository;

    private BehaviorSubject<List<Restaurant>> restaurantsSubject;

    private int numberOfRestaurantsNearbyRequests;

    public RestaurantGatewayImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantsSubject = BehaviorSubject.create();
        this.numberOfRestaurantsNearbyRequests = 0;
    }

    @Override
    public Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {
            //return Observable.just(new Mock().restaurants())
                   // .observeOn(Schedulers.single());
           if(this.numberOfRestaurantsNearbyRequests > 0)
               return this.restaurantsSubject.hide();

            return this.restaurantRepository.getRestaurantsNearby(
                    myLatitude, myLongitude, radius
            )
                    .map(restaurants -> {
                        List<Restaurant> formated = this.formatRestaurants(restaurants);
                        this.restaurantsSubject.onNext(formated);
                        this.numberOfRestaurantsNearbyRequests += 1;
                        return  formated;
                    })
                    .doOnNext((formated) -> {
                        Log.d(TAG, "Number of RestaurantsNearby requests: " + this.numberOfRestaurantsNearbyRequests + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    });
    }

    @Override
    public Observable<Restaurant> getRestaurantById(String restaurantId) {
        return this.restaurantRepository.getRestaurantById(restaurantId)
                .map(restaurant -> this.formatRestaurant(restaurant));
    }


    // Format Restaurant DTO to Business Logic entity
    private List<Restaurant> formatRestaurants(List<RestaurantDTO> listToFormat) {
        List<Restaurant> formattedList = new ArrayList<>();
        if(!listToFormat.isEmpty()) {
            for(RestaurantDTO r: listToFormat) {
                formattedList.add(this.formatRestaurant(r));
            }
        }
        return formattedList;
    }

    private Restaurant formatRestaurant(RestaurantDTO r) {
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
            restaurant.setPhone(r.getPhone());
            restaurant.setWebSite(r.getWebsite());
        }
        return restaurant;
    }

}

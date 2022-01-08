package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantQuery;

import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.decorators.DistanceInfoDecorator;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForList {

    private RestaurantQuery restaurantQuery;

    private TimeProvider timeProvider;

    private DateProvider dateProvider;

    private TimeInfoDecorator timeInfoDecorator;

    private DistanceQuery distanceQuery;

    private DistanceInfoDecorator distanceInfoDecorator;

    public GetRestaurantsForList(RestaurantQuery restaurantQuery, TimeProvider timeProvider, DateProvider dateProvider, DistanceQuery distanceQuery) {
        this.restaurantQuery = restaurantQuery;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
        this.timeInfoDecorator = new TimeInfoDecorator(this.timeProvider, this.dateProvider);
        this.distanceQuery = distanceQuery;
        this.distanceInfoDecorator = new DistanceInfoDecorator(this.distanceQuery);
    }

    public Observable<List<RestaurantVO>> getRestaurantsNearbyAsValueObjectWithDistance(Geolocation myPosition, int radius) {
        return getRestaurantsNearbyAsValueObject(myPosition, radius)
                .flatMap(restaurants ->
                        Observable.fromIterable(restaurants)
                                .flatMap(restaurant ->
                                        this.distanceInfoDecorator.decor(myPosition,restaurant)
                                )
                                .toList().toObservable()
                );
    }

    private Observable<List<RestaurantVO>> getRestaurantsNearbyAsValueObject(Geolocation myPosition, int radius) {
        return handleRestaurants(myPosition, radius)
                .flatMap(restaurants ->
                        Observable.just(formatRestaurants(restaurants))
                );
    }

    private Observable<List<Restaurant>> handleRestaurants(Geolocation myPosition, int radius) {
        return this.restaurantQuery.getRestaurantsNearbyWithDetails(myPosition, radius);
    }

    private List<RestaurantVO> formatRestaurants(List<Restaurant> restaurants) {
        List<RestaurantVO> restaurantVOs = new ArrayList<>();
        if(!restaurants.isEmpty()) {
            for(Restaurant restaurant: restaurants) {
                RestaurantVO restaurantVO = new RestaurantVO(restaurant);
                this.timeInfoDecorator.decor(restaurantVO);
                restaurantVOs.add(restaurantVO);
            }
        }
        return restaurantVOs;
    }

}

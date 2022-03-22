package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantGateway;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.gateways.DistanceGateway;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.decorators.DistanceInfoDecorator;
import com.android.go4lunch.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCase {

    private final RestaurantGateway restaurantGateway;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway
    ) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<List<RestaurantVO>> getRestaurantsNearbyAsValueObject(Geolocation myPosition, int radius) {
        return handleRestaurants(myPosition, radius)
                .flatMap(restaurants ->
                        Observable.just(formatRestaurants(restaurants))
                );
    }

    private Observable<List<Restaurant>> handleRestaurants(Geolocation myPosition, int radius) {
        return this.restaurantGateway.getRestaurantsNearbyWithDetails(myPosition, radius);
    }

    private List<RestaurantVO> formatRestaurants(List<Restaurant> restaurants) {
        List<RestaurantVO> restaurantVOs = new ArrayList<>();
        if(!restaurants.isEmpty()) {
            for(Restaurant restaurant: restaurants) {
                RestaurantVO restaurantVO = new RestaurantVO(restaurant);
                restaurantVOs.add(restaurantVO);
            }
        }
        return restaurantVOs;
    }

}

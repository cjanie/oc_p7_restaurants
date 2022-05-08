package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantByIdUseCase {

    private RestaurantGateway restaurantGateway;

    public GetRestaurantByIdUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<Restaurant> handle(String restaurantId) throws NotFoundException {
        List<Restaurant> restaurantsResults = new ArrayList<>();
        this.restaurantGateway.getRestaurantsNearby().subscribe(restaurantsResults::addAll);
        if(!restaurantsResults.isEmpty()) {
            for(Restaurant r: restaurantsResults) {
                if(r.getId() == restaurantId) {
                    return Observable.just(r);
                }
            }
        }
        throw new NotFoundException();
    }

}

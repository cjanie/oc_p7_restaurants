package com.android.go4lunch.businesslogic.usecases.restaurant;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import io.reactivex.Observable;

public class SearchRestaurantUseCase extends FindVisitorsUseCase {

    private RestaurantGateway restaurantGateway;

    public SearchRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Observable<RestaurantValueObject> handle(String restaurantId) {
        return this.restaurantGateway.getRestaurantById(restaurantId)
                .map(restaurant -> new RestaurantValueObject(restaurant))
                .flatMap(restaurantVO -> this.updateRestaurantWithVisitorCount(restaurantVO));
    }


}

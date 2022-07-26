package com.android.go4lunch.businesslogic.usecases.restaurant;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import io.reactivex.Observable;

public class SearchRestaurantUseCase extends FindVisitorsUseCase {

    private RestaurantGateway restaurantGateway;

    public SearchRestaurantUseCase(RestaurantGateway restaurantGateway, VisitorGateway visitorGateway) {
        this.restaurantGateway = restaurantGateway;
        this.visitorGateway = visitorGateway;
    }

    public Observable<RestaurantValueObject> handle(String restaurantId) {
        return this.restaurantGateway.getRestaurantById(restaurantId)
                .map(restaurant -> new RestaurantValueObject(restaurant))
                .flatMap(restaurantVO -> this.updateRestaurantWithVisitorCount(restaurantVO));
    }


}

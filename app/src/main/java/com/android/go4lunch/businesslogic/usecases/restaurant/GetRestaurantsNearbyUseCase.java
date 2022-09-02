package com.android.go4lunch.businesslogic.usecases.restaurant;

import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.models.RestaurantModel;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsNearbyUseCase extends FindVisitorsUseCase {

    private RestaurantGateway restaurantGateway;

    private RestaurantModel restaurantModel;

    public GetRestaurantsNearbyUseCase(
            RestaurantGateway restaurantGateway,
            VisitorGateway visitorGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.visitorGateway = visitorGateway;

        this.restaurantModel = new RestaurantModel();
    }

    private Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius);
    }

    private Observable<List<RestaurantValueObject>> getFormated(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantModel.formatRestaurantsAsValueObjects(
                this.getRestaurantsNearby(myLatitude, myLongitude, radius)
        )
                .flatMap(restaurantVOs ->
                        this.updateRestaurantsWithVisitorsCount(
                                restaurantVOs,
                                this.getSelections()
                        ));
    }

    public Observable<List<RestaurantValueObject>> handle(Double myLatitude, Double myLongitude, int radius) {
        return this.getFormated(myLatitude, myLongitude, radius);
    }

}

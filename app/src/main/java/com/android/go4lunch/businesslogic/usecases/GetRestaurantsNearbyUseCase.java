package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.entities.Restaurant;

import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.models.RestaurantEntityModel;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsNearbyUseCase {

    private RestaurantGateway restaurantGateway;

    private VisitorGateway visitorGateway;

    private RestaurantEntityModel restaurantEntityModel;

    public GetRestaurantsNearbyUseCase(
            RestaurantGateway restaurantGateway,
            VisitorGateway visitorGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.visitorGateway = visitorGateway;

        this.restaurantEntityModel = new RestaurantEntityModel();
    }

    private Observable<List<Restaurant>> getRestaurantsNearby(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius);
    }
    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }

    public Observable<List<RestaurantValueObject>>handle(Double myLatitude, Double myLongitude, int radius) {
        return this.formatRestaurantsAsValueObjects(myLatitude, myLongitude, radius)
                .flatMap(restaurantVOs -> this.restaurantEntityModel.updateRestaurantsWithVisitorsCount(
                        restaurantVOs,
                        this.getSelections()
                ));
        //return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius);
    }

    public Observable<List<RestaurantValueObject>> formatRestaurantsAsValueObjects(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurantsNearby(myLatitude, myLongitude, radius)
                .map(restaurants -> this.restaurantEntityModel.formatRestaurantsToValueObjects(restaurants));
    }



}

package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.models.RestaurantModel;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import java.util.List;

import io.reactivex.Observable;

public class GetRestaurantsForListUseCase {

    private final String TAG = "GET RESTAURANTS LIST UC";

    private final RestaurantGateway restaurantGateway;

    private final VisitorGateway visitorGateway;

    private final LikeGateway likeGateway;

    private final RestaurantModel restaurantModel;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway,
            VisitorGateway visitorGateway,
            LikeGateway likeGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.visitorGateway = visitorGateway;
        this.likeGateway = likeGateway;

        this.restaurantModel = new RestaurantModel();
    }

    public Observable<List<RestaurantValueObject>> handle(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurantsWithUpdates(myLatitude, myLongitude, radius)
                .doOnNext(restaurantValueObjects -> Log.d(TAG, "--handle : " + Thread.currentThread().getName()));
    }


    // Get Data from gateways
    private Observable<List<Restaurant>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius)
                .doOnNext(restaurants -> Log.d(TAG, "getRestaurants from Gateway " + Thread.currentThread().getName()));
    }

    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections()
                .doOnNext(selections -> Log.d(TAG, "getSelections from Gateway " + Thread.currentThread().getName()));
    }

    private Observable<List<Like>> getLikes() {

        return this.likeGateway.getLikes()
                .doOnNext(likes -> Log.d(TAG, "getLikes from Gateway " + Thread.currentThread().getName()));
    }

    // Transformations

    public Observable<List<RestaurantValueObject>> getRestaurantsWithUpdates(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantModel.formatRestaurantsAsValueObjects(
                this.getRestaurants(myLatitude, myLongitude, radius)
        )
                .flatMap(restaurantVOs ->
                        this.restaurantModel.updateRestaurantsWithVisitorsCount(
                                restaurantVOs,
                                this.getSelections()
                        ))
                .flatMap(restaurantVOs ->
                        this.restaurantModel.updateRestaurantsWithLikesCount(
                                restaurantVOs,
                                this.getLikes()
                ))
                .doOnNext(restaurantsVOs -> Log.d(TAG, "--getRestaurantsWithUpdates : " + Thread.currentThread().getName()));
    }




}

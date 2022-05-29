package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.models.RestaurantEntityModel;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.businesslogic.valueobjects.WorkmateValueObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class GetRestaurantsForListUseCase {

    private final RestaurantGateway restaurantGateway;

    private final VisitorGateway visitorGateway;

    private final LikeGateway likeGateway;

    private final DistanceGateway distanceGateway;

    private final RestaurantEntityModel restaurantEntityModel;

    public GetRestaurantsForListUseCase(
            RestaurantGateway restaurantGateway,
            VisitorGateway visitorGateway,
            LikeGateway likeGateway,
            DistanceGateway distanceGateway
    ) {
        this.restaurantGateway = restaurantGateway;
        this.visitorGateway = visitorGateway;
        this.likeGateway = likeGateway;
        this.distanceGateway = distanceGateway;

        this.restaurantEntityModel = new RestaurantEntityModel();
    }

    public Observable<List<RestaurantValueObject>> handle(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurantsWithUpdates(myLatitude, myLongitude, radius);
    }


    // Get Data from gateways
    private Observable<List<Restaurant>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        return this.restaurantGateway.getRestaurantsNearby(myLatitude, myLongitude, radius);
    }

    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }

    private Observable<List<Like>> getLikes() {
        return this.likeGateway.getLikes();
    }

    private Observable<Long> getDistance(Geolocation myPosition, Geolocation restaurantPosition) {
        return this.distanceGateway.getDistanceInMeter(myPosition, restaurantPosition);
    }

    // Transformations

    public Observable<List<RestaurantValueObject>> getRestaurantsWithUpdates(Double myLatitude, Double myLongitude, int radius) {
        return this.formatRestaurantsAsValueObjects(myLatitude, myLongitude, radius)
                .flatMap(restaurantVOs -> this.updateRestaurantsWithVisitorsCount(restaurantVOs))
                .flatMap(restaurantVOs -> this.updateRestaurantsWithLikesCount(restaurantVOs))

                .flatMap(restaurantVOs ->
                            Observable.fromIterable(restaurantVOs)
                                    .flatMap(restaurantVO ->
                                            this.updateRestaurantWithDistance(restaurantVO, myLatitude, myLongitude)
                                    ).toList().toObservable()
                        );


    }

    public Observable<List<RestaurantValueObject>> formatRestaurantsAsValueObjects(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurants(myLatitude, myLongitude, radius)
                .map(restaurants -> this.restaurantEntityModel.formatRestaurantsToValueObjects(restaurants));
    }

    private Observable<List<RestaurantValueObject>> updateRestaurantsWithVisitorsCount(List<RestaurantValueObject> restaurantVOs) {
        return this.getSelections().map(selections -> {
            List<RestaurantValueObject> restaurantVOsCopy = restaurantVOs;
            if(!restaurantVOsCopy.isEmpty()) {
                for(RestaurantValueObject restaurantVO: restaurantVOsCopy) {
                    int visitorsCount = this.getVisitorsCountByRestaurantId(selections, restaurantVO.getRestaurant().getId());
                    restaurantVO.setVisitorsCount(visitorsCount);
                }
            }
            return restaurantVOsCopy;
        });
    }

    private int getVisitorsCountByRestaurantId(List<Selection> selections, String restaurantId) {
        int count = 0;
        if (!selections.isEmpty()) {
            for (Selection selection : selections) {
                if (selection.getRestaurantId().equals(restaurantId)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private Observable<List<RestaurantValueObject>> updateRestaurantsWithLikesCount(List<RestaurantValueObject> restaurantVOs) {
        return this.getLikes().map(likes -> {
            List<RestaurantValueObject> restaurantVOsCopy = restaurantVOs;
            if(!restaurantVOsCopy.isEmpty()) {
                for(RestaurantValueObject restaurantVO: restaurantVOsCopy) {
                    int likesCount = this.getLikesCountByRestaurantId(likes, restaurantVO.getRestaurant().getId());
                    restaurantVO.setLikesCount(likesCount);
                }
            }
            return restaurantVOsCopy;
        });
    }

    private int getLikesCountByRestaurantId(List<Like> likes, String restaurantId) {
        int count = 0;
        if(!likes.isEmpty()) {
            for(Like like: likes) {
                if(like.getRestaurantId().equals(restaurantId)) {
                    count +=1;
                }
            }
        }
        return count;
    }

    private Observable<RestaurantValueObject> updateRestaurantWithDistance(RestaurantValueObject restaurantVO, Double myLatitude, Double myLongitude) {
        Geolocation myPosition = new Geolocation(myLatitude, myLongitude);
        return this.getDistance(myPosition, restaurantVO.getRestaurant().getGeolocation())
                .map(distance -> {
                    RestaurantValueObject restaurantVOCopy = restaurantVO;
                    restaurantVOCopy.setDistance(distance);
                    return restaurantVOCopy;
                });

    }
}

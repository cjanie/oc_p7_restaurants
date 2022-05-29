package com.android.go4lunch.businesslogic.usecases;

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
                .flatMap(restaurantVOs -> this.updateRestaurantsWithLikesCount(restaurantVOs));
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

}

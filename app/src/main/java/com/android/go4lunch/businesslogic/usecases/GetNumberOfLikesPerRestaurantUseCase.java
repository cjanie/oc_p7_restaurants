package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;

import io.reactivex.Observable;

public class GetNumberOfLikesPerRestaurantUseCase {

    private LikeGateway likeGateway;

    public GetNumberOfLikesPerRestaurantUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public Observable<Integer> handle(String restaurantId) {
        return this.likeGateway.getLikes().map(likes -> {

            int numberOfLikesPerRestaurant = 0;
            if(!likes.isEmpty()) {
                for(Like like : likes) {
                    if(like.getRestaurantId().equals(restaurantId)) {
                        numberOfLikesPerRestaurant += 1;
                    }
                }
            }
            return numberOfLikesPerRestaurant;
        });
    }

}

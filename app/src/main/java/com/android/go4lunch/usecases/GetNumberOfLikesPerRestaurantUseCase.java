package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.models.Like;

import java.util.List;

class GetNumberOfLikesPerRestaurantUseCase {

    private LikeGateway likeGateway;

    public GetNumberOfLikesPerRestaurantUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public int handle(String restaurantId) {
        int numberOfLikesPerRestaurant = 0;
        List<Like> likes = this.likeGateway.getLikes();
        if(!likes.isEmpty()) {
            for(Like like : likes) {
                if(like.getRestaurantId().equals(restaurantId)) {
                    numberOfLikesPerRestaurant += 1;
                }
            }
        }
        return numberOfLikesPerRestaurant;
    }
}

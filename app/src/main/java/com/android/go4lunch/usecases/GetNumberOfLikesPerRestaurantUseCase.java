package com.android.go4lunch.usecases;

import android.util.Log;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.models.Like;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetNumberOfLikesPerRestaurantUseCase {

    private final String TAG = "NUMBER LIKES USE CASE";

    private LikeGateway likeGateway;

    public GetNumberOfLikesPerRestaurantUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public Observable<Integer> handle(String restaurantId) {
        return this.likeGateway.getLikes().map(likes -> {
            Log.d(TAG, "-- handle -- likes size: " + likes.size());

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

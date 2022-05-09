package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.models.Like;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetNumberOfLikesPerRestaurantUseCase {

    private LikeGateway likeGateway;

    public GetNumberOfLikesPerRestaurantUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public Observable<Integer> handle(String restaurantId) {
        int numberOfLikesPerRestaurant = 0;
        List<Like> likesResults = new ArrayList<>();
        this.likeGateway.getLikes().subscribe(likesResults::addAll);
        if(!likesResults.isEmpty()) {
            for(Like like : likesResults) {
                if(like.getRestaurantId().equals(restaurantId)) {
                    numberOfLikesPerRestaurant += 1;
                }
            }
        }
        return Observable.just(numberOfLikesPerRestaurant);
    }
}

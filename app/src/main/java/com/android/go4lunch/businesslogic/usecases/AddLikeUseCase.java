package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AddLikeUseCase {

    private LikeGateway likeGateway;

    public AddLikeUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public Observable<Boolean> handle(String restaurantId, String workmateId) {

        return this.likeGateway.getLikes().map(likes -> {
            boolean found = false;
            if(!likes.isEmpty()) {
                for(Like like: likes) {
                    if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(workmateId)) {
                        found = true;
                        break;
                    }
                }
            }

            if(!found)
                this.likeGateway.add(new Like(restaurantId, workmateId));
            return !found;
        });


/*
        List<Like> likesResults = new ArrayList<>();
        this.likeGateway.getLikes().subscribe(likesResults::addAll);
        boolean found = false;
        for(Like like: likesResults) {
            if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(workmateId)) {
                found = true;
                break;
            }
        }
        if(!found)
            this.likeGateway.add(new Like(restaurantId, workmateId));
        return !found;
*/

    }

}

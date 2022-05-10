package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.models.Like;

import java.util.ArrayList;
import java.util.List;

public class LikeUseCase {

    private LikeGateway likeGateway;

    public LikeUseCase(LikeGateway likeGateway) {
        this.likeGateway = likeGateway;
    }

    public boolean handle(String restaurantId, String workmateId) {
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
    }

}

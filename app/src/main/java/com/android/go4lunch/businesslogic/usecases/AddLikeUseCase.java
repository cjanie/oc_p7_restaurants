package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AddLikeUseCase {

    private LikeGateway likeGateway;

    private SessionGateway sessionGateway;

    public AddLikeUseCase(
            LikeGateway likeGateway, SessionGateway sessionGateway
    ) {
        this.likeGateway = likeGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> handle(String restaurantId) {

        return this.likeGateway.getLikes().flatMap(likes ->
            addToLikes(restaurantId, likes)
        );
    }

    private Observable<Boolean> addToLikes(String restaurantId, List<Like> likes) {
        return this.getSession().map(session -> {
            boolean found = false;
            if(!likes.isEmpty()) {
                for(Like like: likes) {
                    if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(session.getId())) {
                        found = true;
                        break;
                    }
                }
            }

            if(!found) {
                Like newLike = new Like(restaurantId, session.getId());
                this.likeGateway.add(newLike);
            }

            return !found;
        });
    }

    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }

}

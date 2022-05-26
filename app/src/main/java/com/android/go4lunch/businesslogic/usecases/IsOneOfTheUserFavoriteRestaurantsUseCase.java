package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class IsOneOfTheUserFavoriteRestaurantsUseCase {

    private final String TAG = "IS FAVORITE USE CASE";

    private LikeGateway likeGateway;
    private SessionGateway sessionGateway;

    public IsOneOfTheUserFavoriteRestaurantsUseCase(LikeGateway likeGateway, SessionGateway sessionGateway) {
        this.likeGateway = likeGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> handle(String restaurantId) {

        return this.getLikes().flatMap(likes -> this.isFoundAsAFavorite(likes, restaurantId));

    }

    private Observable<List<Like>> getLikes() {
        return this.likeGateway.getLikes();
    }

    private Observable<Boolean> isFoundAsAFavorite(List<Like> likes, String restaurantId) {
        return this.getSession().map(session -> {

            if(!likes.isEmpty()) {
                for(Like like: likes) {
                    if(like.getWorkmateId().equals(session.getId()) && like.getRestaurantId().equals(restaurantId)) {
                        return true;
                    }
                }
            }

            return false;
        });
    }

    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }
}

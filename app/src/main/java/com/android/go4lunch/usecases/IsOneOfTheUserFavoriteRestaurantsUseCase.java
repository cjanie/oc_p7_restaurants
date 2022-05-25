package com.android.go4lunch.usecases;

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

    public Observable<Boolean> handle(String restaurantId) throws NoWorkmateForSessionException {
        String workmateOfSessionId = this.getUserId();
        return this.likeGateway.getLikes().map(likes -> {
            Log.d(TAG, "-- handle -- likes size: " + likes.size());
            boolean isAFavoriteRestaurant = false;
            if(!likes.isEmpty()) {
                for(Like like: likes) {
                    if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(workmateOfSessionId)) {
                        isAFavoriteRestaurant = true;
                        break;
                    }
                }
            }
            return isAFavoriteRestaurant;
        });
    }

    private String getUserId() throws NoWorkmateForSessionException {
        List<Workmate> sessionResults = new ArrayList<>();
        if(this.sessionGateway.getSession() == null)
            throw new NoWorkmateForSessionException();
        this.sessionGateway.getSession().subscribe(sessionResults::add);
        if(sessionResults.isEmpty()) {
            throw new NoWorkmateForSessionException();
        } else {
            Workmate session = sessionResults.get(0);
            return session.getId();
        }
    }
}

package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Like;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class IsOneOfTheUserFavoriteRestaurantsUseCase {

    private LikeGateway likeGateway;
    private SessionGateway sessionGateway;

    public IsOneOfTheUserFavoriteRestaurantsUseCase(LikeGateway likeGateway, SessionGateway sessionGateway) {
        this.likeGateway = likeGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> handle(String restaurantId) throws NoWorkmateForSessionException {
        String workmateOfSessionId = this.getUserId();
        boolean isAFavoriteRestaurant = false;
        List<Like> likesResults = new ArrayList<>();
        this.likeGateway.getLikes().subscribe(likesResults::addAll);
        if(!likesResults.isEmpty()) {
            for(Like like: likesResults) {
                if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(workmateOfSessionId)) {
                    isAFavoriteRestaurant = true;
                    break;
                }
            }
        }
        return Observable.just(isAFavoriteRestaurant);
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

package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.models.LikeModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AddLikeUseCase {

    private final String TAG = "ADD LIKE USE CASE";

    private LikeGateway likeGateway;

    private SessionGateway sessionGateway;

    private final LikeModel likeModel;

    public AddLikeUseCase(
            LikeGateway likeGateway, SessionGateway sessionGateway
    ) {
        this.likeGateway = likeGateway;
        this.sessionGateway = sessionGateway;

        this.likeModel = new LikeModel();
    }

    public Observable<Boolean> handle(String restaurantId) {

        return this.getLikesAndAddToLikesIfDoesNotExist(restaurantId)
                .doOnNext(b ->
                        Log.d(TAG, "--handle : " + Thread.currentThread().getName()));
    }

    private Observable<Boolean> getLikesAndAddToLikesIfDoesNotExist(String restaurantId) {
        return this.addToLikesIfDoesNotExist(restaurantId, this.getLikes());
    }


    private Observable<Boolean> addToLikesIfDoesNotExist(String restaurantId, List<Like> likes) {
        return this.getSession().flatMap(session -> {
            Log.d(TAG, "addToLikes : " + Thread.currentThread().getName());

            boolean doesLikeExist = this.likeModel.doesLikeExist(restaurantId, session.getId(), likes);

            if(!doesLikeExist) {
                Like like = new Like(restaurantId, session.getId());
                this.likeGateway.add(like);
                return Observable.just(true);
            }
            return Observable.just(false);
        });
    }

    private List<Like> getLikes() {
        List<Like> likesResult = new ArrayList<>();
        this.likeGateway.getLikes().subscribe(likes -> likesResult.addAll(likes));
        return likesResult;
    }


    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }

}

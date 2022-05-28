package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryLikeGateway implements LikeGateway {

    Observable<List<Like>> likes;

    public InMemoryLikeGateway() {
        this.likes = Observable.just(new ArrayList<>());
    }

    public void setLikes(List<Like> likes) {
        this.likes = Observable.just(likes);
    }

    @Override
    public Observable<List<Like>> getLikes() {
        return this.likes;
    }

    @Override
    public Observable<Boolean> add(Like like) {
        List<Like> likesResult = new ArrayList<>();
        this.likes.subscribe(likesResult::addAll);
        likesResult.add(like);
        this.likes = Observable.just(likesResult);
        return Observable.just(true);
    }

}


package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Like;

import java.util.List;

import io.reactivex.Observable;

public interface LikeGateway {

    Observable<List<Like>> getLikes();

    boolean add(Like like);
}

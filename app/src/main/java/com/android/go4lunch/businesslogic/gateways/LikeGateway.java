package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.businesslogic.entities.Like;

import java.util.List;

import io.reactivex.Observable;

public interface LikeGateway {

    Observable<List<Like>> getLikes();

    boolean add(Like like);
}

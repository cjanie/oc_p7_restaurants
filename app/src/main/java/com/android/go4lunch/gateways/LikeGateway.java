package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Like;

import java.util.List;

public interface LikeGateway {

    List<Like> getLikes();

    void add(Like like);
}

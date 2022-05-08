package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.models.Like;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLikeGateway implements LikeGateway {
    List<Like> likes;

    public InMemoryLikeGateway() {
        this.likes = new ArrayList<>();
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @Override
    public List<Like> getLikes() {
        return this.likes;
    }

    @Override
    public void add(Like like) {
        this.likes.add(like);
    }


}


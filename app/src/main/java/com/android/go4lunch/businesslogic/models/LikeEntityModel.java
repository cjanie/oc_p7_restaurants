package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Like;

import java.util.List;

public class LikeEntityModel {

    public boolean doesLikeExist(String restaurantId, String workmateId, List<Like> likes) {

        if(!likes.isEmpty()) {
            for(Like like: likes) {
                if(like.getRestaurantId().equals(restaurantId) && like.getWorkmateId().equals(workmateId)) {
                    return true;
                }
            }
        }
        return false;
    }
}

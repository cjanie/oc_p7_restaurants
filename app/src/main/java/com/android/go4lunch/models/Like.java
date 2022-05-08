package com.android.go4lunch.models;

public class Like {

    String restaurantId;

    String workmateId;

    public Like(String restaurantId, String workmateId) {
        this.restaurantId = restaurantId;
        this.workmateId = workmateId;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public String getWorkmateId() {
        return this.workmateId;
    }
}

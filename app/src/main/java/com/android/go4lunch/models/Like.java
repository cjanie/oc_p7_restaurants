package com.android.go4lunch.models;

public class Like {

    String id;

    String restaurantId;

    String workmateId;

    public Like(String restaurantId, String workmateId) {
        this.restaurantId = restaurantId;
        this.workmateId = workmateId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return this.restaurantId;
    }

    public String getWorkmateId() {
        return this.workmateId;
    }
}

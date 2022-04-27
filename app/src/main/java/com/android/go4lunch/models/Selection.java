package com.android.go4lunch.models;

public class Selection {

    private String id;

    private String restaurantId;

    private String workmateId;

    public Selection(String restaurantId, String workmateId) {
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
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getWorkmateId() {
        return workmateId;
    }

    public void setWorkmateId(String workmateId) {
        this.workmateId = workmateId;
    }

}

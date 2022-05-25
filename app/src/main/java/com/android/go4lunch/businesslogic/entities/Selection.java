package com.android.go4lunch.businesslogic.entities;

public class Selection {

    private String id;

    private String restaurantId;

    private String workmateId;

    private String restaurantName;

    public Selection(String restaurantId, String workmateId) {
        this.id = workmateId;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
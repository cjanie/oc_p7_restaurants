package com.android.go4lunch.models;

public class Selection {

    private String restaurantId;

    private String restaurantName;

    private String workmateId;

    private String workmateName;

    public Selection(String restaurantId, String restaurantName, String workmateId, String workmateName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.workmateId = workmateId;
        this.workmateName = workmateName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getWorkmateId() {
        return workmateId;
    }

    public void setWorkmateId(String workmateId) {
        this.workmateId = workmateId;
    }

    public String getWorkmateName() {
        return workmateName;
    }

    public void setWorkmateName(String workmateName) {
        this.workmateName = workmateName;
    }
}

package com.android.go4lunch.models;

public class Selection {

    private Restaurant restaurant;

    private Workmate workmate;

    public Selection(Restaurant restaurant, Workmate workmate) {
        this.restaurant = restaurant;
        this.workmate = workmate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Workmate getWorkmate() {
        return workmate;
    }
}

package com.android.go4lunch.read.businesslogic.usecases.model;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

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

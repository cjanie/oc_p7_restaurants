package com.android.go4lunch.write.businesslogic.usecases.events;

import com.android.go4lunch.models.Restaurant;

public class ToggleSelectionEvent {

    public Restaurant restaurant;

    public ToggleSelectionEvent(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

package com.android.go4lunch.write.businesslogic.usecases.events;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

public class ToggleSelectionEvent {

    public Restaurant restaurant;

    public ToggleSelectionEvent(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}

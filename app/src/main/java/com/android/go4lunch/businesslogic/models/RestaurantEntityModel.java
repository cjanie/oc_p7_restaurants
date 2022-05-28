package com.android.go4lunch.businesslogic.models;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.List;

public class RestaurantEntityModel {

    public Restaurant findWorkmateSelection(String workmateId, List<Selection> selections) {
        if(!selections.isEmpty()) {
            for(Selection selection: selections) {
                if(selection.getWorkmateId().equals(workmateId)) {
                    Restaurant selectedRestaurant = new Restaurant(selection.getRestaurantName());
                    selectedRestaurant.setId(selection.getRestaurantId());
                    return selectedRestaurant;
                }
            }
        }
        return null;
    }
}

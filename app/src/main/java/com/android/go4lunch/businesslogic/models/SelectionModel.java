package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Selection;

public class SelectionModel {

    public Selection createSelection(
            String restaurantId,
            String workmateId,
            String restaurantName,
            String workmateName,
            String workmateUrlPhoto
    ) {
        Selection selection = new Selection(restaurantId, workmateId);
        selection.setRestaurantName(restaurantName);
        selection.setWorkmateName(workmateName);
        selection.setWorkmateUrlPhoto(workmateUrlPhoto);
        return selection;
    }

    public Selection createSelection(
            String restaurantId,
            String workmateId,
            String restaurantName,
            String workmateName,
            String workmateUrlPhoto,
            String selectionId
    ) {
        Selection selection = new Selection(restaurantId, workmateId);
        selection.setId(selectionId);
        selection.setRestaurantName(restaurantName);
        selection.setWorkmateName(workmateName);
        selection.setWorkmateUrlPhoto(workmateUrlPhoto);
        return selection;
    }
}

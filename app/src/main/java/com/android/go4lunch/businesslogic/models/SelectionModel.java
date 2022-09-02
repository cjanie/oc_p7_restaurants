package com.android.go4lunch.businesslogic.models;

import com.android.go4lunch.businesslogic.entities.Selection;

public class SelectionModel {

    public Selection createSelection(
            String restaurantId,
            String workmateId,
            String restaurantName,
            String workmateName,
            String workmateUrlPhoto,
            String restaurantUrlPhoto,
            String restaurantAddress,
            String restaurantPhone,
            String restaurantWebSite
    ) {
        Selection selection = new Selection(restaurantId, workmateId);
        selection.setRestaurantName(restaurantName);
        selection.setWorkmateName(workmateName);
        selection.setWorkmateUrlPhoto(workmateUrlPhoto);
        selection.setRestaurantUrlPhoto(restaurantUrlPhoto);
        selection.setRestaurantAddress(restaurantAddress);
        selection.setRestaurantPhone(restaurantPhone);
        selection.setRestaurantWebSite(restaurantWebSite);
        return selection;
    }

    public Selection createSelection(
            String restaurantId,
            String workmateId,
            String restaurantName,
            String workmateName,
            String workmateUrlPhoto,
            String restaurantUrlPhoto,
            String restaurantAddress,
            String restaurantPhone,
            String restaurantWebSite,
            String selectionId
    ) {
        Selection selection = this.createSelection(
                restaurantId,
                workmateId,
                restaurantName,
                workmateName,
                workmateUrlPhoto,
                restaurantUrlPhoto,
                restaurantAddress,
                restaurantPhone,
                restaurantWebSite);

        selection.setId(selectionId);

        return selection;
    }
}

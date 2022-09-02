package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.models.SelectionModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GoForLunchUseCase {

    private final String TAG = "GO FOR LUNCH USE CASE";

    private final VisitorGateway visitorGateway;

    private final SessionGateway sessionGateway;

    public GoForLunchUseCase(
            VisitorGateway visitorGateway,
            SessionGateway sessionGateway) {
        this.visitorGateway = visitorGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> selectRestaurant(Restaurant restaurant) {
        return this.sessionGateway.getSession().map(session -> {
            Selection newSelection = new Selection(restaurant.getId(), session.getId());
            newSelection.setRestaurantName(restaurant.getName());
            newSelection.setWorkmateName(session.getName());
            newSelection.setWorkmateUrlPhoto(session.getUrlPhoto());
            newSelection.setRestaurantUrlPhoto(restaurant.getPhotoUrl());
            newSelection.setRestaurantAddress(restaurant.getAddress());
            newSelection.setRestaurantPhone(restaurant.getPhone());
            newSelection.setRestaurantWebSite(restaurant.getWebSite());

            this.visitorGateway.addSelection(newSelection);
            return true;
        });
    }

    public Observable<Boolean> unselectRestaurant(String restaurantId) {
        return this.getSession().map(session -> {
            this.getSelections().subscribe(selections -> {
                if(!selections.isEmpty()) {
                    for(Selection selection: selections) {
                        if(selection.getWorkmateId().equals(session.getId()) && selection.getRestaurantId().equals(restaurantId)) {
                            this.visitorGateway.removeSelection(selection.getId());
                        }
                    }
                }
            });
            return true;
        });
    }

    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }

    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }
}

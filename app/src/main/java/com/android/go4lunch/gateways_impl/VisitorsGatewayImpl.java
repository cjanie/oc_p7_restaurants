package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class VisitorsGatewayImpl implements VisitorsGateway {

    private FirebaseFirestore database;

    public VisitorsGatewayImpl(FirebaseFirestore database) {
        this.database = database;
    }

    @Override
    public void addSelection(Selection selection) {
        Map<String, String> selectionMap = new HashMap<>();
        selectionMap.put("restaurantId", selection.getRestaurantId());
        selectionMap.put("workmateId", selection.getWorkmateId());
        DocumentReference newSelectionRef = this.database.collection("selections").document();
        newSelectionRef.set(selectionMap);
    }

    @Override
    public void removeSelection(String workmateId) {

    }

    @Override
    public Observable<List<Selection>> getSelections() {
        return Observable.just(new Mock().selections());
    }

    @Override
    public Observable<List<Selection>> getVisitors(String restaurantId) {
        return Observable.just(new ArrayList<>());
    }

}

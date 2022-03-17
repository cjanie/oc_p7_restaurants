package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryVisitorsGateway implements VisitorsGateway {

    Observable<List<Selection>> selections;

    public InMemoryVisitorsGateway() {
        this.selections = Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<List<Selection>> getSelections() {
        return selections;
    }

    @Override
    public Observable<List<Selection>> getVisitors(String restaurantId) {
        return this.selections;
    }

    public void setSelections(List<Selection> selections) {

        this.selections = Observable.just(selections);
    }

    @Override
    public void addSelection(Selection selection) {
        List<Selection> results = new ArrayList<>();
        this.selections.subscribe(results::addAll);
        results.add(selection);
        this.selections = Observable.just(results);

    }

    @Override
    public void removeSelection(String workmateId) {
        List<Selection> results = new ArrayList<>();
        this.selections.subscribe(results::addAll);
        if(!results.isEmpty()) {
            for(int i=0; i<results.size(); i++) {
                if(results.get(i).getWorkmateId() != null) {
                    if(results.get(i).getWorkmateId().equals(workmateId)) {
                        results.remove(i);
                        break;
                    }
                }

            }
        }
        this.selections = Observable.just(results);
    }
}
package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryVisitorGateway implements VisitorGateway {

    private Observable<List<Selection>> selectionsObservable;

    public InMemoryVisitorGateway() {
        this.selectionsObservable = Observable.just(new ArrayList<>());
    }

    public void setSelections(List<Selection> selections) {
        if(selections != null) {
            this.selectionsObservable = Observable.just(selections);
        }
    }

    @Override
    public Observable<List<Selection>> getSelections() {
        return this.selectionsObservable;
    }

    @Override
    public Observable<List<Selection>> getVisitors(String restaurantId) {
        return this.selectionsObservable;
    }

    @Override
    public void addSelection(Selection selection) {
        List<Selection> results = new ArrayList<>();
        this.selectionsObservable.subscribe(results::addAll);
        results.add(selection);
        this.selectionsObservable = Observable.just(results);
    }

    @Override
    public void removeSelection(String workmateId) {
        List<Selection> results = new ArrayList<>();
        this.selectionsObservable.subscribe(results::addAll);
        if(!results.isEmpty()) {
            for(int i=0; i<results.size(); i++) {
                if(results.get(i).getWorkmateId() != null && results.get(i).getWorkmateId().equals(workmateId)) {
                    results.remove(i);
                    break;
                }
            }
        }
        this.selectionsObservable = Observable.just(results);
    }
}
package com.android.go4lunch.repositories;

import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.gateways.SelectionCommand;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryCurrentSelectionsRepository implements SelectionCommand {

    private Observable<List<Selection>> selections;

    public InMemoryCurrentSelectionsRepository() {

        this.selections = Observable.just(new ArrayList<>());
    }

    public void setSelections(List<Selection> selections) {
        if(selections != null) {
            this.selections = Observable.just(selections);
        }
    }

    @Override
    public Observable<List<Selection>> getSelections() {
        return this.selections;
    }


    @Override
    public void add(Selection selection) {
        List<Selection> results = new ArrayList<>();
        this.selections.subscribe(results::addAll);
        results.add(selection);
        this.selections = Observable.just(results);
    }

    @Override
    public void remove(Selection selection) {
        List<Selection> results = new ArrayList<>();
        this.selections.subscribe(results::addAll);
        if(!results.isEmpty()) {
            for(int i=0; i<results.size(); i++) {
                if(results.get(i).getRestaurant().getId() == selection.getRestaurant().getId()) {
                    results.remove(i);
                    break;
                }
            }
        }
        this.selections = Observable.just(results);
    }


}

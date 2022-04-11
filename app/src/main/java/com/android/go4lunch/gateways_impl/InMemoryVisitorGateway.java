package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class InMemoryVisitorGateway implements VisitorGateway {

    private List<Selection> selections;

    private PublishSubject<List<Selection>> selectionsSubject;

    public InMemoryVisitorGateway() {
        this.selections = new ArrayList<>();
        this.selectionsSubject = PublishSubject.create();
        this.selectionsSubject.onNext(this.selections);
    }

    @Override
    public Observable<List<Selection>> getSelections() {
        return this.selectionsSubject.hide();
    }

    @Override
    public Observable<List<Selection>> getVisitors(String restaurantId) {
        return this.selectionsSubject.hide();
    }

    public void setSelections(List<Selection> selections) {

        this.selectionsSubject.onNext(selections);
    }

    @Override
    public void addSelection(Selection selection) {
        this.selections.add(selection);
        this.selectionsSubject.onNext(selections);
    }

    @Override
    public void removeSelection(String workmateId) {
        if(!this.selections.isEmpty()) {
            for(int i=0; i<this.selections.size(); i++) {
                if(this.selections.get(i).getWorkmateId() != null) {
                    if(this.selections.get(i).getWorkmateId().equals(workmateId)) {
                        this.selections.remove(i);
                        break;
                    }
                }

            }
        }
        this.selectionsSubject.onNext(this.selections);
    }
}
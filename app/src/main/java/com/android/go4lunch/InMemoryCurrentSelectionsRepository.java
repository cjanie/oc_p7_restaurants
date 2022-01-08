package com.android.go4lunch;

import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public class InMemoryCurrentSelectionsRepository implements SelectionRepository {

    private Set<Selection> selections;



    public InMemoryCurrentSelectionsRepository() {
        this.selections = new HashSet<>();
    }

    public void setSelections(Set<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public Observable<Set<Selection>> getSelections() {
        return Observable.just(this.selections);
    }


    @Override
    public void add(Selection selection) {
        if(this.selections.isEmpty()) {
            this.selections.add(selection);
        } else {
            for(Selection s: this.selections) {
                if(!s.getRestaurant().getName().equals(selection.getRestaurant().getName())
                        && !s.getWorkmate().getName().equals(selection.getWorkmate().getName())) {
                    this.selections.add(selection);
                    break;
                }
            }
        }

    }

    @Override
    public void remove(Selection selection) {
        this.selections.remove(selection);
    }


}

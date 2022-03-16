package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

public class InMemoryVisitorsGateway implements VisitorsGateway {

    List<Selection> selections;

    public InMemoryVisitorsGateway() {
        this.selections = new ArrayList<>();
    }


    public List<Selection> getSelections() {
        return selections;
    }

    @Override
    public List<Selection> getVisitors(String restaurantId) {
        return this.selections;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public void addSelection(Selection selection) {
        this.selections.add(selection);
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
    }
}
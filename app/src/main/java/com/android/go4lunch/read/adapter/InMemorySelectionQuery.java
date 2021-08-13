package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemorySelectionQuery implements SelectionQuery {

    private List<Selection> selections;

    public InMemorySelectionQuery() {
        this.selections = new ArrayList<>();
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public List<Selection> findAll() {
        return this.selections;
    }
}

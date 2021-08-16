package com.android.go4lunch;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.HistoricOfSelectionsCommand;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;
import com.android.go4lunch.write.businesslogic.usecases.UpdateHistoric;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCurrentSelectionsRepository implements SelectionRepository {

    private List<Selection> selections;



    public InMemoryCurrentSelectionsRepository() {
        this.selections = new ArrayList<>();
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public List<Selection> findAll() {
        return this.selections;
    }


    @Override
    public void add(Selection selection) {
        this.selections.add(selection);
    }

    @Override
    public void remove(Selection selection) {
        this.selections.remove(selection);
    }


}

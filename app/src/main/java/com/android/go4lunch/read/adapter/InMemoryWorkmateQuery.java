package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.WorkmateQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import java.util.ArrayList;
import java.util.List;

public class InMemoryWorkmateQuery implements WorkmateQuery {

    private List<Workmate> workmates;

    public InMemoryWorkmateQuery() {
        this.workmates = new ArrayList<>();
    }

    public void setWorkmates(List<Workmate> workmates) {
        this.workmates = workmates;
    }

    @Override
    public List<Workmate> findAll() {
        return this.workmates;
    }
}

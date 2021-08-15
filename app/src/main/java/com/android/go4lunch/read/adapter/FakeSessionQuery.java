package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

public class FakeSessionQuery extends InMemorySessionQuery {

    public FakeSessionQuery() {
        this.setWorkmate(new Workmate("Cyril"));
    }
}

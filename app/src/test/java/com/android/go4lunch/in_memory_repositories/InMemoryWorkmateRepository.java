package com.android.go4lunch.in_memory_repositories;

import com.android.go4lunch.gateways.WorkMateQuery;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryWorkmateRepository implements WorkMateQuery {

    private List<Workmate> workmates;

    public InMemoryWorkmateRepository() {
        this.workmates = new ArrayList<>();
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() {
        return Observable.just(this.workmates);
    }

    public void setWorkmates(List<Workmate> workmates) {
        this.workmates = workmates;
    }
}

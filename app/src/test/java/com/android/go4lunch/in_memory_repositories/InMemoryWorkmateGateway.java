package com.android.go4lunch.in_memory_repositories;

import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryWorkmateGateway implements WorkmateGateway {

    private Observable<List<Workmate>> workmates;

    public InMemoryWorkmateGateway() {
        this.workmates = Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() {
        return this.workmates;
    }

    public void setWorkmates(List<Workmate> workmates) {
        this.workmates = Observable.just(workmates);
    }
}

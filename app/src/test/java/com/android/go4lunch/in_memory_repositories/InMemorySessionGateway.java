package com.android.go4lunch.in_memory_repositories;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public class InMemorySessionGateway implements SessionGateway {

    private Observable<Workmate> workmate;

    @Override
    public Observable<Workmate> getWorkmate() {
        return this.workmate;
    }

    public void setWorkmate(Workmate workmate) {
        this.workmate = Observable.just(workmate);
    }
}

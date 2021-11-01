package com.android.go4lunch.in_memory_repositories;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.SessionQuery;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public class InMemorySessionRepository implements SessionQuery {

    private Workmate workmate;

    @Override
    public Observable<Workmate> getWorkmate() throws NoWorkmateForSessionException {
        if(this.workmate == null)
            throw new NoWorkmateForSessionException();
        return Observable.just(this.workmate);
    }

    public void setWorkmate(Workmate workmate) {
        this.workmate = workmate;
    }
}

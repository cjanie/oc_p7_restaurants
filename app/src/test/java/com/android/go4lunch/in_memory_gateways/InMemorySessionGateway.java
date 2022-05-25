package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class InMemorySessionGateway implements SessionGateway {

    private BehaviorSubject<Workmate> sessionSubject;

    public InMemorySessionGateway() {
        this.sessionSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<Workmate> getSession() {
        return this.sessionSubject.hide();
    }

    @Override
    public void signOut() {

    }

    public void setSession(Workmate session) {
        this.sessionSubject.onNext(session);
    }
}

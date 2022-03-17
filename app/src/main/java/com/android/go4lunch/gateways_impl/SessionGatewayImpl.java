package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public class SessionGatewayImpl implements SessionGateway {

    private Observable<Workmate> workmate;

    public SessionGatewayImpl() {
        this.fetchSession();
    }

    @Override
    public Observable<Workmate> getWorkmate()  {
        return this.workmate;
    }

    private void fetchSession() {
        /*
        UserRepository userRepository = new UserRepository();
        userRepository.getAuthUser().addOnSuccessListener(task -> {
            this.workmate = Observable.just(task);
        });

         */
        this.workmate = Observable.just(new Workmate("Cyril"));
    }
}

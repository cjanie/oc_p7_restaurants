package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public interface SessionGateway {

    Observable<Workmate> getSession();

    void signOut();

}

package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.businesslogic.entities.Workmate;

import io.reactivex.Observable;

public interface SessionGateway {

    Observable<Workmate> getSession();

    void signOut();

}

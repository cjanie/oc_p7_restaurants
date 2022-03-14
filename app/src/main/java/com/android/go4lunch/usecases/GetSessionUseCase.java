package com.android.go4lunch.usecases;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;

import io.reactivex.Observable;

public class GetSessionUseCase {

    private SessionGateway sessionGateway;

    public GetSessionUseCase(SessionGateway sessionGateway) {
        this.sessionGateway = sessionGateway;
    }

    public Observable<Workmate> getWorkmate() throws NoWorkmateForSessionException {
        return this.sessionGateway.getWorkmate();
    }

}

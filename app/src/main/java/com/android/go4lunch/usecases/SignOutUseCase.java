package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SessionGateway;

public class SignOutUseCase {

    private SessionGateway sessionGateway;

    public SignOutUseCase(SessionGateway sessionGateway) {
        this.sessionGateway = sessionGateway;
    }

    public void handle() {
        this.sessionGateway.signOut();
    }
}

package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.gateways.MyPositionGateway;

public class GetMyPositionUseCase {

    private MyPositionGateway myPositionGateway;

    public GetMyPositionUseCase(MyPositionGateway myPositionGateway) {
        this.myPositionGateway = myPositionGateway;
    }

    public Geolocation handle() {
        return this.myPositionGateway.getMyPosition();
    }
}

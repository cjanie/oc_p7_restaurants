package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.gateways.MyPositionGateway;

public class SaveMyPositionUseCase {

    private MyPositionGateway myPositionGateway;

    public SaveMyPositionUseCase(MyPositionGateway myPositionGateway) {
        this.myPositionGateway = myPositionGateway;
    }

    public void handle(Double myLatitude, Double myLongitude) {
        this.myPositionGateway.updateMyPosition(new Geolocation(myLatitude, myLongitude));
    }

}
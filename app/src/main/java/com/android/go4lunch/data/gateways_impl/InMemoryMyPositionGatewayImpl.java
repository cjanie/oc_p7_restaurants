package com.android.go4lunch.data.gateways_impl;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.gateways.MyPositionGateway;

public class InMemoryMyPositionGatewayImpl implements MyPositionGateway {

    private Geolocation myPosition;

    @Override
    public void updateMyPosition(Geolocation geolocation) {
        this.myPosition = geolocation;
    }

    @Override
    public Geolocation getMyPosition() {
        return this.myPosition;
    }
}

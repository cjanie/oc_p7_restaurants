package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.businesslogic.entities.Geolocation;

public interface MyPositionGateway {
    void updateMyPosition(Geolocation geolocation);
    Geolocation getMyPosition();
}
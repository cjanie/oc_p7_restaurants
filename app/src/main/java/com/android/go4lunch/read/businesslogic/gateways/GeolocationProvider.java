package com.android.go4lunch.read.businesslogic.gateways;

import android.content.Context;

import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

public interface GeolocationProvider {

    Geolocation here();
}

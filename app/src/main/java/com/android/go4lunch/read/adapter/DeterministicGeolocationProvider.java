package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

public class DeterministicGeolocationProvider implements GeolocationProvider {


    private Geolocation here;

    public DeterministicGeolocationProvider(Geolocation here) {
        this.here = here;
    }

    @Override
    public Geolocation here() {
        return this.here;
    }

    public void setHere(Geolocation here) {
        this.here = here;
    }
}

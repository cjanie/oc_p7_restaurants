package com.android.go4lunch.read.businesslogic.usecases.model;

import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;

public class DistanceInfo {

    private GeolocationProvider geolocationProvider;

    public DistanceInfo(GeolocationProvider geolocationProvider) {
        this.geolocationProvider = geolocationProvider;
    }

    private Geolocation getHere() {
        return this.geolocationProvider.here();
    }


    public long handle(Geolocation remote) {
        return this.getSquareDistanceRoundDown(this.getHere(), remote);
    }

    private long getSquareDistanceRoundDown(Geolocation a, Geolocation b) {
        long roundDown = (long) Math.floor(this.getSquareDistance(a, b));
        return roundDown;
    }

    private Double getSquareDistance(Geolocation a, Geolocation b) {
        Double squareDistance = 0D;
        Double distanceOnAxisX;
        Double distanceOnAxisY;

        if(a != null && b != null) {
            if(a.getX() < b.getX())
                distanceOnAxisX = b.getX() - a.getX();
            else distanceOnAxisX = a.getX() - b.getX();

            if(a.getY() < b.getY())
                distanceOnAxisY = b.getY() - a.getY();
            else distanceOnAxisY = a.getY() - b.getY();
            squareDistance = distanceOnAxisX + distanceOnAxisY;
        }

        return squareDistance;
    }

}

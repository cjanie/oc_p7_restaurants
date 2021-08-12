package com.android.go4lunch.read.businesslogic.usecases.model;

import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;

public class DistanceInfo {

    private GeolocationProvider geolocationProvider;

    public DistanceInfo() {

    }

    public DistanceInfo(GeolocationProvider geolocationProvider) {
        this.geolocationProvider = geolocationProvider;
    }

    public Long getDistance(Geolocation remote) {
        return this.getSquareDistanceRoundDown(geolocationProvider.here(), remote);
    }

    public Long getSquareDistanceRoundDown(Geolocation a, Geolocation b) {
        Long roundDown = (long) Math.floor(this.getSquareDistance(a, b));
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

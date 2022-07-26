package com.android.go4lunch.ui.utils;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.RectangularBounds;

public class RectangularBoundsFactory {

    private Geolocation myPosition;

    private Double forkOfLatitudes;

    private Double forkOfLongitudes;

    public RectangularBoundsFactory(Geolocation myPosition, Double forkOfLatitudes, Double forkOfLongitudes) {
        this.myPosition = myPosition;
        this.forkOfLatitudes = forkOfLatitudes;
        this.forkOfLongitudes = forkOfLongitudes;
    }

    public RectangularBounds create() {
        LatLng southWest = new LatLng(this.latitudeSouthToMyPosition(), this.longitudeWestToMyPosition());
        LatLng northEast = new LatLng(this.latitudeNorthToMyPosition(), this.longitudeEastToMyPosition());
        RectangularBounds rectangularBounds = RectangularBounds.newInstance(southWest, northEast);
        return rectangularBounds;
    }

    private Double latitudeNorthToMyPosition() {
        return myPosition.getLatitude() + forkOfLatitudes / 2;
    }

    private Double latitudeSouthToMyPosition() {
        return myPosition.getLatitude() - forkOfLatitudes / 2;
    }

    private Double longitudeEastToMyPosition() {
        return myPosition.getLongitude() + forkOfLongitudes / 2;
    }

    private Double longitudeWestToMyPosition() {
        return myPosition.getLongitude() - forkOfLongitudes / 2;
    }


}

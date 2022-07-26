package com.android.go4lunch.ui.utils;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.RectangularBounds;

public class RectangularBoundsFactory {

    private Geolocation myPosition;

    private Double forkOfLatitudesAndLongitudes;



    public RectangularBoundsFactory(Geolocation myPosition, Double forkOfLatitudesAndLongitudes) {
        this.myPosition = myPosition;
        this.forkOfLatitudesAndLongitudes = forkOfLatitudesAndLongitudes;
    }

    public RectangularBounds create() {
        LatLng southWest = new LatLng(this.latitudeSouthToMyPosition(), this.longitudeWestToMyPosition());
        LatLng northEast = new LatLng(this.latitudeNorthToMyPosition(), this.longitudeEastToMyPosition());
        RectangularBounds rectangularBounds = RectangularBounds.newInstance(southWest, northEast);
        return rectangularBounds;
    }

    private Double latitudeNorthToMyPosition() {
        return myPosition.getLatitude() + forkOfLatitudesAndLongitudes / 2;
    }

    private Double latitudeSouthToMyPosition() {
        return myPosition.getLatitude() - forkOfLatitudesAndLongitudes / 2;
    }

    private Double longitudeEastToMyPosition() {
        return myPosition.getLongitude() + forkOfLatitudesAndLongitudes / 2;
    }

    private Double longitudeWestToMyPosition() {
        return myPosition.getLongitude() - forkOfLatitudesAndLongitudes / 2;
    }


}

package com.android.go4lunch.ui.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CenterCamera {

    public LatLng getCenter(List<LatLng> latLngs) {

        List<Double> latitudes = new ArrayList<>();
        List<Double> longitudes = new ArrayList<>();
        for(LatLng latLng: latLngs) {
            // Latitudes
            Double latitude = latLng.latitude;
            latitudes.add(latitude);
            // Longitudes
            Double longitude = latLng.longitude;
            longitudes.add(longitude);
        }
        Collections.sort(latitudes);
        Collections.sort(longitudes);

        Double forkOfLatitudes = (latitudes.get(latitudes.size() - 1) - latitudes.get(0));
        Double centerLatitude = latitudes.get(0) + forkOfLatitudes / 2;

        Double forkOfLontitudes = (longitudes.get(longitudes.size() - 1) - longitudes.get(0));
        Double centerLongitude = longitudes.get(0) + forkOfLontitudes / 2;

        return new LatLng(centerLatitude, centerLongitude);
    }
}

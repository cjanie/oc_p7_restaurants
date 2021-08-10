package com.android.go4lunch.read.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

public class GPSGeolocationProvider implements GeolocationProvider {


    private Application application;

    public GPSGeolocationProvider(Application application) {
        this.application = application;
    }

    @Override
    public Geolocation here() {
        Geolocation geolocation = null;
        Location location = this.getHere();
        if(location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            geolocation = new Geolocation(lng, lat);
        }
        return geolocation;
    }

    private Location getHere() {
        if (ActivityCompat.checkSelfPermission(this.application, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String context = Context.LOCATION_SERVICE;
            LocationManager locationManager = (LocationManager) this.application.getSystemService(context);

            String gpsProvider = LocationManager.GPS_PROVIDER;
            Location location = locationManager.getLastKnownLocation(gpsProvider);
            return location;
        }
        return null;
    }

}

package com.android.go4lunch.read.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;

public class AndroidLocationProvider implements GeolocationProvider {


    private Activity activity;

    private FusedLocationProviderClient locationProviderClient;

    private Geolocation myPosition;

    public AndroidLocationProvider(Activity activity) {

        this.activity = activity;
        this.locationProviderClient = LocationServices.getFusedLocationProviderClient(this.activity);
        if(ActivityCompat.checkSelfPermission(
                this.activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            this.locationProviderClient.getLastLocation()
                    .addOnSuccessListener(this.activity, location -> {
                        if(location != null) {
                            Double x = location.getLatitude();
                            Double y = location.getLongitude();
                            myPosition = new Geolocation(x, y);
                            System.out.println("longitude: " + x + "xxxxxxxxxxxxxxxxxxxx");
                        } else {
                            System.out.println("location is null");
                        }
                    })
                    .addOnFailureListener(this.activity, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("getlastlocation failed" + "************" + e.getMessage());
                        }
                    });
        } else {
            this.showEducationalUI();
        }

    }

    @Override
    public Geolocation here() {
        return this.myPosition;
    }

    private Location getHere() {
        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String context = Context.LOCATION_SERVICE;
            LocationManager locationManager = (LocationManager) this.activity.getSystemService(context);

            String gpsProvider = LocationManager.GPS_PROVIDER;
            Location location = locationManager.getLastKnownLocation(gpsProvider);
            return location;
        }
        return null;
    }

    private void showEducationalUI() {
        // Explain to the user that
        // the feature is unavailable because the features requires a permission that the user has denied.
        System.out.println("education UI ****************");
    }

}

package com.android.go4lunch.ui;

import android.location.Location;
import android.location.LocationProvider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

public class LocationViewModel extends ViewModel {

    private MutableLiveData<DistanceInfo> distanceInfo;

    public LocationViewModel() {
        this.distanceInfo = new MutableLiveData<>();
    }

    public void setDistanceInfoValue(DistanceInfo distanceInfo) {
        this.distanceInfo.setValue(distanceInfo);
    }
}

package com.android.go4lunch.ui;

import com.android.go4lunch.ui.utils.CenterCamera;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CenterCameraTest {

    @Test
    public void atTheMiddleOfLatitudeLimits() {
        LatLng latLng1 = new LatLng(8.0, 1.6);
        LatLng latLng2 = new LatLng(7.0, 1.0);
        LatLng latLng3 = new LatLng(4.0, 6.0);
        List<LatLng> latLngs = Arrays.asList(latLng1, latLng2, latLng3);
        LatLng center = new CenterCamera().getCenter(latLngs);
        assert(center.latitude == 6.0);
    }

    @Test
    public void atTheMiddleOfLongitudeLimits() {
        LatLng latLng1 = new LatLng(8.0, 2.0);
        LatLng latLng2 = new LatLng(7.0, 2.5);
        LatLng latLng3 = new LatLng(4.0, 6.0);
        List<LatLng> latLngs = Arrays.asList(latLng1, latLng2, latLng3);
        LatLng center = new CenterCamera().getCenter(latLngs);
        assert(center.longitude == 4.0);
    }

}

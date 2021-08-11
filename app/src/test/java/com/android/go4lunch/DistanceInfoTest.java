package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicGeolocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

import org.junit.Test;

public class DistanceInfoTest {

    @Test
    public void pointATopointBMakingHalfSquare() {
        Geolocation a = new Geolocation(1D, 2D);
        Geolocation b = new Geolocation(3D, 1D);
        this.checkAssertedDistance(a, b, 3);
    }

    @Test
    public void pointAToPointBMakingHalfSquare2() {
        Geolocation a = new Geolocation(5D, 2D);
        Geolocation b = new Geolocation(2D, 3D);
        this.checkAssertedDistance(a, b, 4);
    }

    @Test
    public void pointAToPointBMakingHalfSquare3() {
        Geolocation a = new Geolocation(3D, 2D);
        Geolocation b = new Geolocation(1D, 3D);
        this.checkAssertedDistance(a, b, 3);
    }

    @Test
    public void pointATopointBMakingHalfSquare4() {
        Geolocation a = new Geolocation(3D, 3D);
        Geolocation b = new Geolocation(1D, 2D);
        this.checkAssertedDistance(a, b, 3);
    }

    @Test
    public void pointAToPointBStraightHorizontally() {
        Geolocation a = new Geolocation(3D, 3D);
        Geolocation b = new Geolocation(1D, 3D);
        this.checkAssertedDistance(a, b, 2);
    }

    @Test
    public void pointAToPointBStraightVertically() {
        Geolocation a = new Geolocation(1D, 0D);
        Geolocation b = new Geolocation(1D, 1D);
        this.checkAssertedDistance(a, b, 1);
    }

    @Test
    public void shouldReturn0WhenPointsAreEqual() {
        Geolocation a = new Geolocation(0D, 0D);
        Geolocation b = new Geolocation(0D, 0D);
        this.checkAssertedDistance(a, b, 0);
    }

    @Test
    public void shouldRoundDown() {
        Geolocation a = new Geolocation(0.5, 1D);
        Geolocation b = new Geolocation(1D, 2D);
        this.checkAssertedDistance(a,b, 1);
    }

    @Test
    public void shouldRoundDown2() {
        Geolocation a = new Geolocation(0.5, 1D);
        Geolocation b = new Geolocation(2D, 3D);
        this.checkAssertedDistance(a,b, 3);

    }


    private void checkAssertedDistance(Geolocation here, Geolocation remote, long distanceExpected) {
        DeterministicGeolocationProvider geolocationProvider = new DeterministicGeolocationProvider(here);
        assert(new DistanceInfo(geolocationProvider).getDistance(remote) == distanceExpected);
    }

}

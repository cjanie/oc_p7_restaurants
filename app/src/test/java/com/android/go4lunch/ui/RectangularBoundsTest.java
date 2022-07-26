package com.android.go4lunch.ui;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.ui.utils.RectangularBoundsFactory;

import org.junit.Test;

public class RectangularBoundsTest {

    Double forkOfLatitudes = 0.005947700000007217;
    Double forkOfLongitudes = 0.011622000000000021;


    @Test
    public void myPosition() {

        // My position
        Geolocation myPosition = new Geolocation(1.1, 1.1);
        Double north = myPosition.getLatitude() + forkOfLatitudes / 2;
        assertEquals(north, new RectangularBoundsFactory(myPosition, forkOfLatitudes, forkOfLongitudes).create().getNortheast().latitude, 0);
    }
}

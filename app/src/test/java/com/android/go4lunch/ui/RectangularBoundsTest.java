package com.android.go4lunch.ui;

import static org.junit.Assert.assertEquals;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.ui.utils.RectangularBoundsFactory;

import org.junit.Test;

public class RectangularBoundsTest {

    Double fork = 0.012;

    @Test
    public void myPosition() {

        // My position
        Geolocation myPosition = new Geolocation(1.1, 1.1);
        Double north = myPosition.getLatitude() + fork / 2;
        assertEquals(north, new RectangularBoundsFactory(myPosition, fork).create().getNortheast().latitude, 0);
    }
}

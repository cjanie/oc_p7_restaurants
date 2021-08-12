package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.adapter.DeterministicGeolocationProvider;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.decorators.DistanceInfoDecorator;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import org.junit.Test;

public class DistanceInfoDecoratorTest {


    private RestaurantVO initRestaurantVOWithGeolocation(Geolocation geolocation) {
        Restaurant restaurant = new Restaurant("Janie", "Hello");
        restaurant.setGeolocation(geolocation);
        return new RestaurantVO(restaurant);
    }

    private void checkAssertedDistance(Geolocation myPosition, Geolocation remote, long distanceExpected) {
        assert(new DistanceInfoDecorator(this.initRestaurantVOWithGeolocation(remote))
                .decor(myPosition).getDistanceInfo() == distanceExpected);
    }

    @Test
    public void pointAToPointBMakingHalfSquare() {
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

}

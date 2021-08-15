package com.android.go4lunch.read.adapter;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FakeRestaurantQuery extends InMemoryRestaurantQuery {

    public FakeRestaurantQuery() {

        Restaurant r1 = new Restaurant("nm", "loc");
        r1.setOpen(LocalTime.of(9, 0));
        r1.setClose(LocalTime.of(23, 30));
        r1.setGeolocation(new Geolocation(33.421974, -162.0842122));
        Restaurant r2 = new Restaurant("nm2", "loc");
        r2.setOpen(LocalTime.of(9, 0));
        r2.setClose(LocalTime.of(16, 30));
        r2.setGeolocation(new Geolocation(47.421894, -142.0842122));
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(r1);
        restaurants.add(r2);

        this.setRestaurants(restaurants);

    }
}

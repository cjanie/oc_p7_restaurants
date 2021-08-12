package com.android.go4lunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class MapViewModel extends ViewModel {

    RetrieveRestaurants retrieveRestaurants;

    public MapViewModel() {
        // FAKE DATA QUERY USING INMEMORY // TODO: API
        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        Restaurant r1 = new Restaurant("nm", "loc");
        r1.setOpen(LocalTime.of(9, 0));
        r1.setClose(LocalTime.of(23, 30));
        r1.setGeolocation(new Geolocation(33.421974, -162.0842122));
        Restaurant r2 = new Restaurant("nm2", "loc");
        r2.setOpen(LocalTime.of(9, 0));
        r2.setClose(LocalTime.of(16, 30));
        r2.setGeolocation(new Geolocation(47.421894, -142.0842122));
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[]{r1, r2}));

        this.retrieveRestaurants = new RetrieveRestaurants(restaurantQuery);
    }

    public LiveData<List<RestaurantVO>> list() {
        MutableLiveData<List<RestaurantVO>> restaurants = new MutableLiveData<>();
        restaurants.setValue(this.retrieveRestaurants.handleVO());
        return restaurants;
    }
}

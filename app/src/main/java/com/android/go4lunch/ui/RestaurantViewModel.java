package com.android.go4lunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class RestaurantViewModel extends ViewModel {

    private final RetrieveRestaurants retrieveRestaurants;

    private final TimeProvider timeProvider;

    public RestaurantViewModel() {
        // FAKE DATA QUERY USING INMEMORY // TODO: API
        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        Restaurant r1 = new Restaurant("nm", new CustomLocation("loc"));
        r1.setOpen(LocalTime.of(9, 0));
        r1.setClose(LocalTime.of(23, 30));
        Restaurant r2 = new Restaurant("nm2", new CustomLocation("loc"));
        r2.setOpen(LocalTime.of(9, 0));
        r2.setClose(LocalTime.of(16, 30));
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[]{r1, r2}));

        this.retrieveRestaurants = new RetrieveRestaurants(restaurantQuery); // TODO inject with API DATA
        this.timeProvider = new DeterministicTimeProvider(LocalTime.now());
    }

    public LiveData<List<RestaurantVO>> list() {
        MutableLiveData<List<RestaurantVO>> mRestaurants = new MutableLiveData<>();
        mRestaurants.setValue(this.retrieveRestaurants.handleVO(this.timeProvider));
        return mRestaurants;
    }

}

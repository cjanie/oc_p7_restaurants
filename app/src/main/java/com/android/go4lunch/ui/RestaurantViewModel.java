package com.android.go4lunch.ui;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.read.adapter.DeterministicGeolocationProvider;
import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.GPSGeolocationProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.gateways.GeolocationProvider;
import com.android.go4lunch.read.businesslogic.gateways.RestaurantQuery;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.CustomLocation;
import com.android.go4lunch.read.businesslogic.usecases.model.DistanceInfo;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private final RetrieveRestaurants retrieveRestaurants;

    private final TimeProvider timeProvider;
    private final GeolocationProvider geolocationProvider;

    public RestaurantViewModel(Application application) {
        super(application);
        // FAKE DATA QUERY USING INMEMORY // TODO: API
        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        Restaurant r1 = new Restaurant("nm", "loc");
        r1.setOpen(LocalTime.of(9, 0));
        r1.setClose(LocalTime.of(23, 30));
        r1.setGeolocation(new Geolocation(1D, 3D));
        Restaurant r2 = new Restaurant("nm2", "loc");
        r2.setOpen(LocalTime.of(9, 0));
        r2.setClose(LocalTime.of(16, 30));
        r2.setGeolocation(new Geolocation(2D, -4D));
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[]{r1, r2}));
        this.timeProvider = new DeterministicTimeProvider(LocalTime.now());
        this.geolocationProvider = new DeterministicGeolocationProvider(new Geolocation(110D, 210D)); // TODO with geoloc android tool
        //this.geolocationProvider = new GPSGeolocationProvider(this.getApplication());
        this.retrieveRestaurants = new RetrieveRestaurants(restaurantQuery); // TODO inject with API DATA
    }


    public LiveData<List<RestaurantVO>> list() {
        MutableLiveData<List<RestaurantVO>> mRestaurants = new MutableLiveData<>();
        mRestaurants.setValue(this.retrieveRestaurants.handleVO(this.timeProvider, this.geolocationProvider));
        return mRestaurants;
    }

}

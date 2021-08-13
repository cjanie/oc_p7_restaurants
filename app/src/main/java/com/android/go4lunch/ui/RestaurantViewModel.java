package com.android.go4lunch.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.adapter.InMemorySelectionQuery;
import com.android.go4lunch.read.adapter.RealTimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private final RetrieveRestaurants retrieveRestaurants;

    private final InMemorySelectionQuery selectionQuery;

    public RestaurantViewModel(Application application) {
        super(application);
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

        this.selectionQuery = new InMemorySelectionQuery();
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(r1, new Workmate("Janie")));
        selectionQuery.setSelections(selections);
    }


    public LiveData<List<RestaurantVO>> list() {
        MutableLiveData<List<RestaurantVO>> mRestaurants = new MutableLiveData<>();
        List<RestaurantVO> list = this.retrieveRestaurants.handle();
        if(!list.isEmpty()) {
            for(RestaurantVO r: list) {
                r = new TimeInfoDecorator(new RealTimeProvider(), r).decor();
                r = new SelectionInfoDecoratorForRestaurant(this.selectionQuery, r).decor();
            }
        }
        mRestaurants.setValue(list);

        return mRestaurants;
    }

}

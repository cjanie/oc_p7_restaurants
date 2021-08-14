package com.android.go4lunch.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.InMemorySelectionRepository;
import com.android.go4lunch.read.adapter.InMemorySessionQuery;
import com.android.go4lunch.read.adapter.RealTimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveSession;
import com.android.go4lunch.read.businesslogic.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;
import com.android.go4lunch.write.businesslogic.usecases.IncrementSelectionsCount;
import com.android.go4lunch.write.businesslogic.usecases.ToggleSelection;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private final RetrieveRestaurants retrieveRestaurants;

    private final InMemorySelectionRepository selectionQuery;

    private final RetrieveSession retrieveSession;

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

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();
        this.selectionQuery = new InMemorySelectionRepository(historicRepository);
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(r1, new Workmate("Janie")));
        selectionQuery.setSelections(selections);

        InMemorySessionQuery sessionQuery = new InMemorySessionQuery();
        sessionQuery.setWorkmate(new Workmate("Cyril"));
        this.retrieveSession = new RetrieveSession(sessionQuery);
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

    public void toggleSelection(Restaurant restaurant) {
        new ToggleSelection(this.selectionQuery, this.retrieveSession, restaurant).toggle();
    }
}

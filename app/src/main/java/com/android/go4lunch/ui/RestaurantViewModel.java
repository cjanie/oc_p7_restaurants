package com.android.go4lunch.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;


import com.android.go4lunch.InMemoryCurrentSelectionsRepository;
import com.android.go4lunch.providers.RealTimeProvider;

import com.android.go4lunch.providers.RealDateProvider;

import com.android.go4lunch.repositories.DistanceRepository;
import com.android.go4lunch.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;

import com.android.go4lunch.usecases.decorators.VoteInfoDecorator;
import com.android.go4lunch.usecases.decorators.VoteResult;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.models.Selection;

import com.android.go4lunch.repositories.RestaurantRepository;
import com.android.go4lunch.usecases.GetRestaurantsForList;
import com.android.go4lunch.write.businesslogic.usecases.ToggleSelection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantViewModel extends AndroidViewModel {

    private MutableLiveData<List<RestaurantVO>> mRestaurants;

    // Data
    private final GetRestaurantsForList getRestaurantsForList;

    //private final RetrieveSession retrieveSession;


    // Decor
    private final TimeInfoDecorator timeInfoDecorator;

    private final SelectionInfoDecoratorForRestaurant selectionInfoDecorator;

    private VoteInfoDecorator voteInfoDecorator;

    // Action
    //private final ToggleSelection toggleSelection;


    public RestaurantViewModel(Application application) {
        super(application);
        this.mRestaurants = new MutableLiveData<>(new ArrayList<>());
        // TODO: replace fake with API
        this.getRestaurantsForList = new GetRestaurantsForList(new RestaurantRepository(), new RealTimeProvider(), new RealDateProvider(), new DistanceRepository());
        //this.retrieveSession = new RetrieveSession(new FakeSessionQuery());
        this.timeInfoDecorator = new TimeInfoDecorator(new RealTimeProvider(), new RealDateProvider());

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();

        InMemoryCurrentSelectionsRepository selectionQuery = new InMemoryCurrentSelectionsRepository();
        List<Selection> selections = new ArrayList<>();
        //selections.add(new Selection(this.getRestaurants.handle().get(0).getRestaurant(), new Workmate("Janie")));
        //selectionQuery.setSelections(selections);


        this.selectionInfoDecorator = new SelectionInfoDecoratorForRestaurant(selectionQuery);
        // Vote
        VoteResult voteResult = new VoteResult(historicRepository);
        this.voteInfoDecorator = new VoteInfoDecorator(voteResult);

        // Select
        //this.toggleSelection = new ToggleSelection(selectionQuery, this.retrieveSession, historicRepository);
    }

    public void setRestaurants(List<RestaurantVO> restaurants) {
        this.mRestaurants.setValue(restaurants);
    }

    public LiveData<List<RestaurantVO>> getRestaurants() {
        return this.mRestaurants;
    }


    public LiveData<List<RestaurantVO>> list() {
        MutableLiveData<List<RestaurantVO>> mRestaurants = new MutableLiveData<>();
        List<RestaurantVO> list = new ArrayList<>();// this.getRestaurantsForList.getRestaurantsNearbyAsValueObject(latitude, longitude, radius);
        if(!list.isEmpty()) {
            for(RestaurantVO r: list) {
                r = this.timeInfoDecorator.decor(r);
                //r = this.selectionInfoDecorator.decor(r);
                r = this.voteInfoDecorator.decor(r);
            }
        }
        mRestaurants.setValue(list);

        return mRestaurants;
    }

    /*
    public void toggleSelection(Restaurant restaurant) {
        this.toggleSelection.toggle(restaurant);
    }

*/
}

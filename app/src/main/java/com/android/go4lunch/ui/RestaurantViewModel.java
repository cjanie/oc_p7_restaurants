package com.android.go4lunch.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.read.adapter.FakeRestaurantQuery;
import com.android.go4lunch.read.adapter.FakeSessionQuery;
import com.android.go4lunch.InMemoryCurrentSelectionsRepository;
import com.android.go4lunch.read.adapter.RealTimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveSession;
import com.android.go4lunch.read.businesslogic.usecases.decorators.SelectionInfoDecoratorForRestaurant;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.decorators.VoteInfoDecorator;
import com.android.go4lunch.read.businesslogic.usecases.VoteResult;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;
import com.android.go4lunch.write.businesslogic.usecases.ToggleSelection;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private final RetrieveRestaurants retrieveRestaurants;

    private final RetrieveSession retrieveSession;


    private final TimeInfoDecorator timeInfoDecorator;

    private final SelectionInfoDecoratorForRestaurant selectionInfoDecorator;

    private VoteInfoDecorator voteInfoDecorator;


    private final ToggleSelection toggleSelection;


    public RestaurantViewModel(Application application) {
        super(application);
        // TODO: replace fake with API
        this.retrieveRestaurants = new RetrieveRestaurants(new FakeRestaurantQuery());
        this.retrieveSession = new RetrieveSession(new FakeSessionQuery());
        this.timeInfoDecorator = new TimeInfoDecorator(new RealTimeProvider());

        InMemoryHistoricOfSelectionsRepository historicRepository = new InMemoryHistoricOfSelectionsRepository();

        InMemoryCurrentSelectionsRepository selectionQuery = new InMemoryCurrentSelectionsRepository(historicRepository);
        List<Selection> selections = new ArrayList<>();
        selections.add(new Selection(this.retrieveRestaurants.handle().get(0).getRestaurant(), new Workmate("Janie")));
        selectionQuery.setSelections(selections);


        this.selectionInfoDecorator = new SelectionInfoDecoratorForRestaurant(selectionQuery);
        // Vote
        VoteResult voteResult = new VoteResult(historicRepository);
        this.voteInfoDecorator = new VoteInfoDecorator(voteResult);

        // Command
        this.toggleSelection = new ToggleSelection(selectionQuery, this.retrieveSession);


    }


    public LiveData<List<RestaurantVO>> list() {
        MutableLiveData<List<RestaurantVO>> mRestaurants = new MutableLiveData<>();
        List<RestaurantVO> list = this.retrieveRestaurants.handle();
        if(!list.isEmpty()) {
            for(RestaurantVO r: list) {
                r = this.timeInfoDecorator.decor(r);
                r = this.selectionInfoDecorator.decor(r);
                r = this.voteInfoDecorator.decor(r);
            }
        }
        mRestaurants.setValue(list);

        return mRestaurants;
    }

    public void toggleSelection(Restaurant restaurant) {
        this.toggleSelection.toggle(restaurant);
    }
}

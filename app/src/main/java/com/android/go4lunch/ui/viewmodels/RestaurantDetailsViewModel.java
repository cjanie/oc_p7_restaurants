package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.AddSelection;
import com.android.go4lunch.usecases.GetSession;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private AddSelection addSelection;

    private GetSession getSession;

    private MutableLiveData<Restaurant> restaurant;

    public RestaurantDetailsViewModel(
            AddSelection addSelection,
            GetSession getSession) {
        this.addSelection = addSelection;
        this.getSession = getSession;
        this.restaurant = new MutableLiveData<>();
    }

    public void addSelection() throws NoWorkmateForSessionException {
        Observable<Workmate> observableWorkmate = this.getSession.getWorkmate();
        List<Workmate> results = new ArrayList<>();
        observableWorkmate.subscribe(results::add);
        this.addSelection.add(new Selection(this.restaurant.getValue(), results.get(0)));
    }
}

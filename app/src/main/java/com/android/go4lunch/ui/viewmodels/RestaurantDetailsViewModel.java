package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.ToggleSelection;
import com.android.go4lunch.usecases.GetSession;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private ToggleSelection toggleSelection;

    private GetSession getSession;

    private MutableLiveData<Restaurant> restaurant;

    public RestaurantDetailsViewModel(
            ToggleSelection toggleSelection,
            GetSession getSession) {
        this.toggleSelection = toggleSelection;
        this.getSession = getSession;
        this.restaurant = new MutableLiveData<>();
    }

    public void handleLike() throws NoWorkmateForSessionException {
        Observable<Workmate> observableWorkmate = this.getSession.getWorkmate();
        List<Workmate> results = new ArrayList<>();
        observableWorkmate.subscribe(results::add);
        this.toggleSelection.handle(new Selection(this.restaurant.getValue(), results.get(0)));
    }
}

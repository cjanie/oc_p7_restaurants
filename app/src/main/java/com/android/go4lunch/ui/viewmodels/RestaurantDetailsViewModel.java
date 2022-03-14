package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.ToggleSelectionUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private ToggleSelectionUseCase toggleSelectionUseCase;

    private GetSessionUseCase getSessionUseCase;

    private MutableLiveData<Restaurant> restaurant;

    public RestaurantDetailsViewModel(
            ToggleSelectionUseCase toggleSelectionUseCase,
            GetSessionUseCase getSessionUseCase) {
        this.toggleSelectionUseCase = toggleSelectionUseCase;
        this.getSessionUseCase = getSessionUseCase;
        this.restaurant = new MutableLiveData<>();
    }

    public void handleLike() throws NoWorkmateForSessionException {
        Observable<Workmate> observableWorkmate = this.getSessionUseCase.getWorkmate();
        List<Workmate> results = new ArrayList<>();
        observableWorkmate.subscribe(results::add);
        this.toggleSelectionUseCase.handle(new Selection(this.restaurant.getValue(), results.get(0)));
    }
}

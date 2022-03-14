package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
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

    private MutableLiveData<List<Workmate>> visitors;

    public RestaurantDetailsViewModel(
            ToggleSelectionUseCase toggleSelectionUseCase,
            GetSessionUseCase getSessionUseCase) {
        this.toggleSelectionUseCase = toggleSelectionUseCase;
        this.getSessionUseCase = getSessionUseCase;
        this.restaurant = new MutableLiveData<>();
        Workmate janie = new Workmate("Janie");
        List<Workmate> workmates = new ArrayList<>();
        workmates.add(janie);
        this.visitors = new MutableLiveData<>(workmates);
    }

    public LiveData<Restaurant> getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant.setValue(restaurant);
    }

    public LiveData<List<Workmate>> getVisitors() {
        return this.visitors;
    }

    public void handleLike() throws NoWorkmateForSessionException {
        Observable<Workmate> observableWorkmate = this.getSessionUseCase.getWorkmate();
        List<Workmate> results = new ArrayList<>();
        observableWorkmate.subscribe(results::add);
        this.toggleSelectionUseCase.handle(new Selection(this.restaurant.getValue(), results.get(0)));
    }

}

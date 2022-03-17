package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.ToggleSelectionUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private ToggleSelectionUseCase toggleSelectionUseCase;

    private GetSessionUseCase getSessionUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private MutableLiveData<Restaurant> restaurant;

    private MutableLiveData<List<Workmate>> visitors;

    private Disposable disposable;

    public RestaurantDetailsViewModel(
            ToggleSelectionUseCase toggleSelectionUseCase,
            GetSessionUseCase getSessionUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase) {
        this.toggleSelectionUseCase = toggleSelectionUseCase;
        this.getSessionUseCase = getSessionUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;

        this.restaurant = new MutableLiveData<>();
        this.visitors = new MutableLiveData<>(new ArrayList<>());
        this.fetchVisitors();
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
        this.toggleSelectionUseCase.handle(new Selection(
                this.restaurant.getValue().getId(),
                this.restaurant.getValue().getName(),
                results.get(0).getId(),
                results.get(0).getName()));
    }

    private void fetchVisitors() {
        if(this.restaurant.getValue() != null) {
            List<Workmate> results = new ArrayList<>();
            this.getRestaurantVisitorsUseCase.handle(this.restaurant.getValue().getId()).subscribe(results::addAll);
            System.out.println("fetch visitors" + results.size());
            this.visitors.setValue(results);
        }



    }


}

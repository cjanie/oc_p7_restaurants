package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.ToggleSelectionUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private ToggleSelectionUseCase toggleSelectionUseCase;

    private GetSessionUseCase getSessionUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private MutableLiveData<Restaurant> restaurant;

    private MutableLiveData<List<Workmate>> visitors;

    public RestaurantDetailsViewModel(
            ToggleSelectionUseCase toggleSelectionUseCase,
            GetSessionUseCase getSessionUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase) {
        this.toggleSelectionUseCase = toggleSelectionUseCase;
        this.getSessionUseCase = getSessionUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;

        this.restaurant = new MutableLiveData<>();
        List<Workmate> workmates = new ArrayList<>();
        if(this.restaurant.getValue() != null) {
            workmates = this.fetchVisitors(restaurant.getValue().getId());
        }
        workmates = this.fetchVisitors("1");
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
        this.toggleSelectionUseCase.handle(new Selection(
                this.restaurant.getValue().getId(),
                this.restaurant.getValue().getName(),
                results.get(0).getId(),
                results.get(0).getName()));
    }

    private List<Workmate> fetchVisitors(String restaurantId) {
        return this.getRestaurantVisitorsUseCase.handle(restaurantId);
    }


}

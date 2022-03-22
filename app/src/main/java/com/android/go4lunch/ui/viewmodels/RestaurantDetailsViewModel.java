package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.LikeForLunchUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private GetSessionUseCase getSessionUseCase;

    private LikeForLunchUseCase likeForLunchUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private Restaurant restaurant;

    private Workmate session;

    private MutableLiveData<List<Workmate>> visitors;

    private MutableLiveData<Boolean> isTheCurrentSelection;

    private Disposable disposable;

    public RestaurantDetailsViewModel(
            GetSessionUseCase getSessionUseCase,
            LikeForLunchUseCase likeForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.likeForLunchUseCase = likeForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.visitors = new MutableLiveData<>(new ArrayList<>());
        this.isTheCurrentSelection = new MutableLiveData<>();
    }


    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.fetchVisitors();
    }

    private void setSession() throws NoWorkmateForSessionException {
        List<Workmate> sessionResults = new ArrayList<>();
        this.getSessionUseCase.getWorkmate().subscribe(sessionResults::add);
        if(!sessionResults.isEmpty())
            this.session = sessionResults.get(0);
    }

    public LiveData<List<Workmate>> getVisitors() {
        return this.visitors;
    }

    public LiveData<Boolean> getIsTheCurrentSelection() throws NoWorkmateForSessionException {
        return this.fetchIsTheCurrentSelection();
    };

    public void handleLike() throws NoWorkmateForSessionException {
        this.setSession();
        if(this.session != null) {
            this.likeForLunchUseCase.handle(
                    this.restaurant.getId(),
                    this.restaurant.getName(),
                    this.session.getId(),
                    this.session.getName()
                    );
        }
        this.fetchVisitors();
        this.fetchIsTheCurrentSelection();
    }

    private void fetchVisitors() {
        if(this.restaurant != null) {
            List<Workmate> results = new ArrayList<>();
            this.getRestaurantVisitorsUseCase.handle(this.restaurant.getId()).subscribe(results::addAll);
            this.visitors.postValue(results);
        }
    }

    private LiveData<Boolean> fetchIsTheCurrentSelection() throws NoWorkmateForSessionException {
        this.setSession();
        if(this.restaurant != null && this.session != null) {
            List<Boolean> isTheCurrentSelectionResults = new ArrayList<>();
            this.isTheCurrentSelectionUseCase.handle(
                        this.restaurant.getId(),
                        this.session.getId()
                    ).subscribe(isTheCurrentSelectionResults::add);
            if(!isTheCurrentSelectionResults.isEmpty())
                this.isTheCurrentSelection.postValue(isTheCurrentSelectionResults.get(0));
        }
        return this.isTheCurrentSelection;
    }

}

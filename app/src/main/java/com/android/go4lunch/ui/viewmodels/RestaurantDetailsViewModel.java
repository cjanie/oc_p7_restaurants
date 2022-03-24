package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.LikeUseCase;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private GetSessionUseCase getSessionUseCase;

    private LikeUseCase likeUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private GetWorkmateByIdUseCase getWorkmateByIdUsecase;

    private Restaurant restaurant;

    private Workmate session;

    private MutableLiveData<List<Workmate>> visitors;

    private MutableLiveData<Boolean> isTheCurrentSelection;

    private Disposable disposable;

    public RestaurantDetailsViewModel(
            GetSessionUseCase getSessionUseCase,
            LikeUseCase likeUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUsecase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.likeUseCase = likeUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.getWorkmateByIdUsecase = getWorkmateByIdUsecase;

        this.visitors = new MutableLiveData<>(new ArrayList<>());
        this.isTheCurrentSelection = new MutableLiveData<>();
    }


    public void setRestaurant(Restaurant restaurant) throws NotFoundException {
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

    public void handleLike() throws NotFoundException {
        this.setSession();
        if(this.session != null) {
        this.fetchIsTheCurrentSelection();
            this.likeUseCase.handle(
                    this.restaurant.getId(),
                    this.session.getId()
                    );
        }
        this.fetchVisitors();
        this.fetchIsTheCurrentSelection();
    }

    private void fetchVisitors() throws NotFoundException {
        if(this.restaurant != null) {
            List<String> workmateIdsResults = new ArrayList<>();
            this.getRestaurantVisitorsUseCase.handle(this.restaurant.getId()).subscribe(workmateIdsResults::addAll);

            List<Workmate> workmates = new ArrayList<>();
            if(!workmateIdsResults.isEmpty()) {
                for(String id: workmateIdsResults) {
                    List<Workmate> workmateResults = new ArrayList<>();
                    try {
                        this.getWorkmateByIdUsecase.handle(id).subscribe(workmateResults::add);
                        Workmate workmate = workmateResults.get(0);
                        workmates.add(workmate);
                    } catch (NotFoundException e) {
                        throw e;
                    }
                }
            }
            this.visitors.postValue(workmates);
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

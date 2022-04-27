package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GoForLunchUseCase;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private GetSessionUseCase getSessionUseCase;

    private GoForLunchUseCase goForLunchUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private GetWorkmateByIdUseCase getWorkmateByIdUsecase;

    private Restaurant restaurant;

    private Workmate session;

    private MutableLiveData<List<Workmate>> visitors;

    private MutableLiveData<Boolean> isTheCurrentSelection;

    public RestaurantDetailsViewModel(
            GetSessionUseCase getSessionUseCase,
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUsecase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.getWorkmateByIdUsecase = getWorkmateByIdUsecase;

        this.visitors = new MutableLiveData<>(new ArrayList<>());
        this.isTheCurrentSelection = new MutableLiveData<>(false);

    }


    public void setRestaurant(Restaurant restaurant) throws NotFoundException {
        this.restaurant = restaurant;
        this.fetchVisitors();
    }

    private void setSession() throws NoWorkmateForSessionException {
        List<Workmate> sessionResults = new ArrayList<>();
        this.getSessionUseCase.handle().subscribe(sessionResults::add);
        if(!sessionResults.isEmpty())
            this.session = sessionResults.get(0);
    }

    public LiveData<List<Workmate>> getVisitors() {
        return this.visitors;
    }

    public LiveData<Boolean> getIsTheCurrentSelection() {
        return this.isTheCurrentSelection;
    };

    public void handleLike() throws NotFoundException {
        this.setSession();
        if(this.session != null) {
            this.goForLunchUseCase.handle(
                    this.restaurant.getId(),
                    this.session.getId()
                    );
        }
        this.fetchVisitors();
    }

    private void fetchVisitors() throws NotFoundException {
        if(this.restaurant != null) {
            List<String> workmateIdsResults = new ArrayList<>();
            this.getRestaurantVisitorsUseCase.handle(this.restaurant.getId()).subscribe(workmateIdsResults::addAll);

            if(workmateIdsResults.isEmpty()) {
                isTheCurrentSelection.setValue(false);
            }

            List<Workmate> workmates = new ArrayList<>();
            if(!workmateIdsResults.isEmpty()) {
                for(String id: workmateIdsResults) {
                    List<Workmate> workmateResults = new ArrayList<>();
                    try {
                        this.getWorkmateByIdUsecase.handle(id).subscribe(workmateResults::add);
                        Workmate workmate = workmateResults.get(0);
                        workmates.add(workmate);
                        this.setSession();
                        if(this.session != null) {
                            if(workmate.getId().equals(this.session.getId())) {
                                this.isTheCurrentSelection.setValue(true);
                            } else {
                                this.isTheCurrentSelection.setValue(false);
                            }
                        }

                    } catch (NotFoundException e) {
                        throw e;
                    }
                }
            }

            this.visitors.postValue(workmates);
        }
    }

}

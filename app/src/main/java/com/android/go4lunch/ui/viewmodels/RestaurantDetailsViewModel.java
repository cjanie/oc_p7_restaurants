package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.businesslogic.usecases.IsOneOfTheUserFavoriteRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.businesslogic.usecases.AddLikeUseCase;
import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.GoForLunchUseCase;
import com.android.go4lunch.businesslogic.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private GetSessionUseCase getSessionUseCase;

    private GoForLunchUseCase goForLunchUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private GetWorkmateByIdUseCase getWorkmateByIdUsecase;

    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private AddLikeUseCase addLikeUseCase;

    private IsOneOfTheUserFavoriteRestaurantsUseCase isOneOfTheUserFavoriteRestaurantsUseCase;

    private Restaurant restaurant;

    private Workmate session;

    private MutableLiveData<List<Workmate>> visitors;

    private MutableLiveData<Boolean> isTheCurrentSelectionLiveData;

    private MutableLiveData<Boolean> isOneOfTheUserFavoriteRestaurants;

    public RestaurantDetailsViewModel(
            GetSessionUseCase getSessionUseCase,
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUsecase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            AddLikeUseCase addLikeUseCase,
            IsOneOfTheUserFavoriteRestaurantsUseCase isOneOfTheUserFavoriteRestaurantsUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.getWorkmateByIdUsecase = getWorkmateByIdUsecase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.addLikeUseCase = addLikeUseCase;
        this.isOneOfTheUserFavoriteRestaurantsUseCase = isOneOfTheUserFavoriteRestaurantsUseCase;

        this.visitors = new MutableLiveData<>(new ArrayList<>());
        this.isTheCurrentSelectionLiveData = new MutableLiveData<>(false);
        this.isOneOfTheUserFavoriteRestaurants = new MutableLiveData<>(false);
    }


    public void setRestaurant(Restaurant restaurant) throws NotFoundException {
        this.restaurant = restaurant;
        this.fetchVisitors();
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
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
        // fetch -> void
        // get -> LiveData
        //this.updateIsTheCurrentSelectionLiveData();
        return this.isTheCurrentSelectionLiveData;
    }

    public void updateIsTheCurrentSelectionLiveData() {
        if(this.restaurant != null) {
            this.isTheCurrentSelectionUseCase.handle(this.restaurant.getId()).subscribe(
                    isTheCurrentSelection -> {
                        this.isTheCurrentSelectionLiveData.postValue(isTheCurrentSelection);
                    },
                    Throwable::printStackTrace
            );
        }
    }

    public LiveData<Boolean> getIsOneOfTheUserFavoriteRestaurants() throws NoWorkmateForSessionException {
        return this.isOneOfTheUserFavoriteRestaurants;
    }

    public void updateIsOneOfTheUserFavoriteRestaurants() throws NoWorkmateForSessionException {
        if(this.restaurant != null) {
            this.isOneOfTheUserFavoriteRestaurantsUseCase.handle(this.restaurant.getId()).subscribe(
                    isFavorite -> {
                        this.isOneOfTheUserFavoriteRestaurants.postValue(isFavorite);
                    }
            );
        }
    }

    public void handleGoForLunch() throws NotFoundException {
        this.setSession();
        if(this.session != null) {
            this.goForLunchUseCase.handle(
                    this.restaurant.getId(),
                    this.session.getId(),
                    this.restaurant.getName()
                    );
            // TODO
            Observable.just(true).delay(2, TimeUnit.SECONDS).subscribe(bool -> {
                this.updateIsTheCurrentSelectionLiveData();
                //this.fetchVisitors();
            });

        }

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
                        this.setSession();

                    } catch (NotFoundException e) {
                        throw e;
                    }
                }
            }

            this.visitors.postValue(workmates);
        }
    }

    public void handleLike() throws NoWorkmateForSessionException {
        this.setSession();
        if(session != null) {
            this.addLikeUseCase.handle(this.restaurant.getId(), this.session.getId());
            this.updateIsOneOfTheUserFavoriteRestaurants();
        }
    }

}

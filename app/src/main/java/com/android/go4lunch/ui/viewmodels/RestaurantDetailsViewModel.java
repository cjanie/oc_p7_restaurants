package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.usecases.IsOneOfTheUserFavoriteRestaurantsUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.LikeUseCase;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GoForLunchUseCase;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private GetSessionUseCase getSessionUseCase;

    private GoForLunchUseCase goForLunchUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private GetWorkmateByIdUseCase getWorkmateByIdUsecase;

    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private LikeUseCase likeUseCase;

    private IsOneOfTheUserFavoriteRestaurantsUseCase isOneOfTheUserFavoriteRestaurantsUseCase;

    private Restaurant restaurant;

    private Workmate session;

    private MutableLiveData<List<Workmate>> visitors;

    private MutableLiveData<Boolean> isTheCurrentSelection;

    private MutableLiveData<Boolean> isOneOfTheUserFavoriteRestaurants;

    public RestaurantDetailsViewModel(
            GetSessionUseCase getSessionUseCase,
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUsecase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            LikeUseCase likeUseCase,
            IsOneOfTheUserFavoriteRestaurantsUseCase isOneOfTheUserFavoriteRestaurantsUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.getWorkmateByIdUsecase = getWorkmateByIdUsecase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.likeUseCase = likeUseCase;
        this.isOneOfTheUserFavoriteRestaurantsUseCase = isOneOfTheUserFavoriteRestaurantsUseCase;

        this.visitors = new MutableLiveData<>(new ArrayList<>());
        this.isTheCurrentSelection = new MutableLiveData<>(false);
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
        //this.fetchIsTheCurrentSelection();
        return this.isTheCurrentSelection;
    }

    public void updateIsTheCurrentSelection() {
        if(this.restaurant != null) {
            this.isTheCurrentSelectionUseCase.handle(this.restaurant.getId()).subscribe(
                    isTheCurrentSelection -> {
                        this.isTheCurrentSelection.postValue(isTheCurrentSelection);
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
                    this.session.getId()
                    );
            // TODO
            Observable.just(true).delay(2, TimeUnit.SECONDS).subscribe(bool -> {
                this.updateIsTheCurrentSelection();
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
            this.likeUseCase.handle(this.restaurant.getId(), this.session.getId());
            this.updateIsOneOfTheUserFavoriteRestaurants();
        }
    }

}

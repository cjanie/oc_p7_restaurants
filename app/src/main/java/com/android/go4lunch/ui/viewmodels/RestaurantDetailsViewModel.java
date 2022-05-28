package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.businesslogic.usecases.IsInFavoritesRestaurantsUseCase;
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

    private IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase;

    //DATA
    private Restaurant restaurant;

    private Workmate session;

    private MutableLiveData<List<Workmate>> visitorsLiveData;

    private MutableLiveData<Boolean> isTheCurrentSelectionLiveData;

    private MutableLiveData<Boolean> isMarkedAsFavoriteLiveData;

    public RestaurantDetailsViewModel(
            GetSessionUseCase getSessionUseCase,
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            GetWorkmateByIdUseCase getWorkmateByIdUsecase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            AddLikeUseCase addLikeUseCase,
            IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase
    ) {
        this.getSessionUseCase = getSessionUseCase;
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.getWorkmateByIdUsecase = getWorkmateByIdUsecase;
        this.isTheCurrentSelectionUseCase = isTheCurrentSelectionUseCase;
        this.addLikeUseCase = addLikeUseCase;
        this.isInFavoritesRestaurantsUseCase = isInFavoritesRestaurantsUseCase;

        this.visitorsLiveData = new MutableLiveData<>(new ArrayList<>());
        this.isTheCurrentSelectionLiveData = new MutableLiveData<>(false);
        this.isMarkedAsFavoriteLiveData = new MutableLiveData<>(false);
    }


    public void setRestaurant(Restaurant restaurant) throws NotFoundException {
        this.restaurant = restaurant;
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

    public LiveData<Boolean> getIsTheCurrentSelection() {
        return this.isTheCurrentSelectionLiveData;
    }

    public void fetchIsTheCurrentSelectionToUpdateLiveData() {
        if(this.restaurant != null) {
            this.isTheCurrentSelectionUseCase.handle(this.restaurant.getId())
                    .subscribe(isTheCurrentSelection ->
                        this.isTheCurrentSelectionLiveData.postValue(isTheCurrentSelection)
            );
        }
    }

    public LiveData<Boolean> getIsMarkedAsFavoriteLiveData() {
        return this.isMarkedAsFavoriteLiveData;
    }

    public void fetchIsMarkedAsFavoriteToUpdateLiveData() {
        if(this.restaurant != null) {
            this.isInFavoritesRestaurantsUseCase.handle(this.restaurant.getId()).subscribe(
                    isFavorite -> {
                        this.isMarkedAsFavoriteLiveData.postValue(isFavorite);
                    }
            );
        }
    }

    public LiveData<List<Workmate>> getVisitorsLiveData() {
        return this.visitorsLiveData;
    }

    public void fetchVisitorsToUpdateLiveData() {
        if(this.restaurant != null) {
            this.getRestaurantVisitorsUseCase.handle(this.restaurant.getId())
                    .subscribe(visitors ->
                            this.visitorsLiveData.postValue(visitors)
                    );
        }
    }

    public void handleGoForLunch() {

        this.goForLunchUseCase.handle(this.restaurant.getId(), this.restaurant.getName()).delay(5, TimeUnit.SECONDS)
                .subscribe(isDone -> {

            });
        this.fetchIsTheCurrentSelectionToUpdateLiveData();
    }



    public void handleLike() {

        this.addLikeUseCase.handle(this.restaurant.getId())
                .subscribe(isDone ->
                        this.fetchIsMarkedAsFavoriteToUpdateLiveData()
                );


    }

}

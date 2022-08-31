package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.IsInFavoritesRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.businesslogic.usecases.AddLikeUseCase;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.GoForLunchUseCase;
import com.android.go4lunch.businesslogic.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailsViewModel extends ViewModel {

    // Use cases
    private GoForLunchUseCase goForLunchUseCase;

    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    private AddLikeUseCase addLikeUseCase;

    private IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase;

    //DATA
    private Restaurant restaurant;

    private MutableLiveData<List<Workmate>> visitorsLiveData;

    private MutableLiveData<Boolean> isTheCurrentSelectionLiveData;

    private MutableLiveData<Boolean> isMarkedAsFavoriteLiveData;

    public RestaurantDetailsViewModel(
            GoForLunchUseCase goForLunchUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase,
            AddLikeUseCase addLikeUseCase,
            IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase
    ) {
        this.goForLunchUseCase = goForLunchUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
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

    public LiveData<Boolean> getIsTheCurrentSelection() {
        return this.isTheCurrentSelectionLiveData;
    }

    public void fetchIsTheCurrentSelectionToUpdateLiveData() {
        if(this.restaurant != null) {
            this.isTheCurrentSelectionUseCase.handle(this.restaurant.getId())

                    .subscribe(isTheCurrentSelection ->
                        this.isTheCurrentSelectionLiveData.postValue(isTheCurrentSelection),
                            Throwable::printStackTrace
            );
        }
    }

    public LiveData<Boolean> getIsMarkedAsFavoriteLiveData() {
        return this.isMarkedAsFavoriteLiveData;
    }

    public void fetchIsMarkedAsFavoriteToUpdateLiveData() {
        if(this.restaurant != null) {
            this.isInFavoritesRestaurantsUseCase.handle(this.restaurant.getId())

                    .subscribe(isFavorite ->
                            this.isMarkedAsFavoriteLiveData.postValue(isFavorite),
                            Throwable::printStackTrace
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
                            this.visitorsLiveData.postValue(visitors),
                            Throwable::printStackTrace
                    );
        }
    }

    public void selectRestaurant() {
        System.out.println("Details ViewModel " + "select this.restaurant is : " + this.restaurant);
        this.goForLunchUseCase.selectRestaurant(this.restaurant)
                .subscribe(isDone -> {
                    this.fetchIsTheCurrentSelectionToUpdateLiveData();
                },
                        Throwable::printStackTrace);
    }

    public void unselectRestaurant() {
        System.out.println("Details ViewModel " +  "unselect this.restaurant is : " + this.restaurant);
        this.goForLunchUseCase.unselectRestaurant(this.restaurant.getId())
                .subscribe(
                        isDone -> this.fetchIsTheCurrentSelectionToUpdateLiveData(),
                        Throwable::printStackTrace);
    }

    public void handleLike() {

        this.addLikeUseCase.handle(this.restaurant.getId())
                .subscribe(isDone ->
                        this.fetchIsMarkedAsFavoriteToUpdateLiveData(),
                        Throwable::printStackTrace
                );

    }

}

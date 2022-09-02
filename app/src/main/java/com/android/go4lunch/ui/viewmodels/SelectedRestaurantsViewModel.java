package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.FilterSelectedRestaurantsUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.ui.loader.LoadingException;
import com.android.go4lunch.ui.presenters.RestaurantListController;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class SelectedRestaurantsViewModel extends ViewModel {

    private FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    // List Presenter
    private final RestaurantListController restaurantListController;

    private final MutableLiveData<List<RestaurantValueObject>> restaurants;


    public SelectedRestaurantsViewModel(
            FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase,
            GetNumberOfLikesPerRestaurantUseCase likeUseCase,
            GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase
    ) {
        this.filterSelectedRestaurantsUseCase = filterSelectedRestaurantsUseCase;
        this.timeProvider = new RealTimeProvider();
        this.dateProvider = new RealDateProvider();
        this.restaurantListController = new RestaurantListController(likeUseCase, distanceUseCase);

        this.restaurants = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<RestaurantValueObject>> getRestaurants() {
        return this.restaurants;
    }

    public void fetchRestaurantsToUpdateLiveData(Double myLatitude, Double myLongitude, int radius) {
        Observable<List<RestaurantValueObject>> restaurantsObservable = this.filterSelectedRestaurantsUseCase.handle(myLatitude, myLongitude, radius);
        restaurantsObservable = this.restaurantListController.updateRestaurantsWithDistance(restaurantsObservable, myLatitude, myLongitude);
        restaurantsObservable = this.restaurantListController.updateRestaurantsWithLikesCount(restaurantsObservable);
        restaurantsObservable = this.restaurantListController.updateRestaurantsWithTimeInfo(restaurantsObservable, this.timeProvider, this.dateProvider);
        restaurantsObservable.subscribe(
                restaurants -> {
                    this.restaurants.postValue(restaurants);
                },
                error -> {
                    error.printStackTrace();
                    throw new LoadingException(error.getClass() + " " + error.getMessage());
                }
        );
    }

}

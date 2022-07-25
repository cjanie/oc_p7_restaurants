package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.ui.LoadingException;
import com.android.go4lunch.ui.presenters.RestaurantListPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantsViewModel extends ViewModel {

    private final String TAG = "RESTAURANTS VIEW MODEL";

    // Use Cases
    private final GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    private final GetNumberOfLikesPerRestaurantUseCase likeUseCase;

    private final GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    // List Presenter
    private final RestaurantListPresenter presenter;

    // List LiveData
    private final MutableLiveData<List<RestaurantValueObject>> restaurantsLiveData;

    private final MutableLiveData<Boolean> isLoadingLiveData;

    // Constructor
    public RestaurantsViewModel(
            GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase,
            GetNumberOfLikesPerRestaurantUseCase likeUseCase,
            GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.likeUseCase = likeUseCase;
        this.distanceUseCase = distanceUseCase;

        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;

        this.presenter = new RestaurantListPresenter(this.likeUseCase, this.distanceUseCase);

        this.restaurantsLiveData = new MutableLiveData<>(new ArrayList<>());
        this.isLoadingLiveData = new MutableLiveData<>(true);
    }

    // Getter for the view the model livedata that the activity listens
    public LiveData<List<RestaurantValueObject>> getRestaurantsLiveData() {
        return this.restaurantsLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return this.isLoadingLiveData;
    }

    // Action
    public void fetchRestaurantsObservableToUpdateLiveData(Double myLatitude, Double myLongitude, int radius) {
        Observable<List<RestaurantValueObject>> restaurantsObservable = this.getRestaurantsNearbyUseCase.handle(myLatitude, myLongitude, radius);
        restaurantsObservable = this.presenter.updateRestaurantsWithDistance(restaurantsObservable, myLatitude, myLongitude);
        restaurantsObservable = this.presenter.updateRestaurantsWithLikesCount(restaurantsObservable);
        restaurantsObservable = this.presenter.updateRestaurantsWithTimeInfo(restaurantsObservable, this.timeProvider, this.dateProvider);
        restaurantsObservable.subscribe(restaurants -> {
                    this.restaurantsLiveData.postValue(restaurants);
                    this.isLoadingLiveData.postValue(false);
                },
                error -> {
                    this.isLoadingLiveData.postValue(false);
                    error.printStackTrace();
                    throw new LoadingException(error.getClass() + " " + error.getMessage());
                },
                () ->
                        this.isLoadingLiveData.postValue(false)
        );
    }

}

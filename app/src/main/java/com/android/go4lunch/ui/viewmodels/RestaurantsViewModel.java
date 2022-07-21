package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.usecases.UpdateRestaurantWithDistanceUseCase;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsForListUseCase;
import com.android.go4lunch.ui.LoadingException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RestaurantsViewModel extends ViewModel {

    private final String TAG = "RESTAURANTS VIEW MODEL";

    // Use Cases
    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final UpdateRestaurantWithDistanceUseCase updateRestaurantWithDistanceUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    // List LiveData
    private final MutableLiveData<List<RestaurantValueObject>> restaurantsLiveData;

    private final MutableLiveData<Boolean> isLoadingLiveData;

    private final MutableLiveData<String> errorLiveData;

    // Constructor
    public RestaurantsViewModel(
            GetRestaurantsForListUseCase getRestaurantsForListUseCase,
            UpdateRestaurantWithDistanceUseCase updateRestaurantWithDistanceUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider) {
        this.getRestaurantsForListUseCase = getRestaurantsForListUseCase;
        this.updateRestaurantWithDistanceUseCase = updateRestaurantWithDistanceUseCase;

        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;

        this.restaurantsLiveData = new MutableLiveData<>(new ArrayList<>());
        this.isLoadingLiveData = new MutableLiveData<>(true);
        this.errorLiveData = new MutableLiveData<>();
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
        this.getRestaurantsForListUseCase.handle(myLatitude, myLongitude, radius)
                .map(restaurantVOs -> updateRestaurantsWithTimeInfo(
                      restaurantVOs, this.timeProvider, this.dateProvider))

                .flatMap(restaurantVOs -> this.updateRestaurantsWithDistance(restaurantVOs, myLatitude, myLongitude))
                .doOnNext(restaurantVOs -> Log.d(TAG, "-- fetchRestaurantsObservableToUpdateLiveData : " + Thread.currentThread().getName()))
                .subscribe(
                        restaurants -> {
                            this.restaurantsLiveData.postValue(restaurants);
                            this.isLoadingLiveData.postValue(false);
                        },
                        error -> {
                            this.isLoadingLiveData.postValue(false);
                            error.printStackTrace();
                            throw new LoadingException();
                        },
                        () ->
                            this.isLoadingLiveData.postValue(false)
                );
    }

    public Observable<List<RestaurantValueObject>> updateRestaurantsWithDistance(List<RestaurantValueObject> restaurantVOs, Double myLatitude, Double myLongitude) {
        return Observable.fromIterable(restaurantVOs)
                .flatMap(restaurantVO ->
                        this.updateRestaurantWithDistanceUseCase.handle(restaurantVO, myLatitude, myLongitude)
                ).toList().toObservable()
                .doOnNext(restaurantValueObjects -> Log.d(TAG, "-- updateRestaurantsWithDistance : " + Thread.currentThread().getName()))
        ;
    }

    public List<RestaurantValueObject> updateRestaurantsWithTimeInfo(List<RestaurantValueObject> restaurantVOs, TimeProvider timeProvider, DateProvider dateProvider) {
        List<RestaurantValueObject> restaurantVOsCopy = restaurantVOs;
        if(!restaurantVOsCopy.isEmpty()) {
            for(RestaurantValueObject restaurantVO: restaurantVOsCopy) {
                restaurantVO.setTimeInfo(timeProvider, dateProvider);
                restaurantVO.setOpenHoursToday(dateProvider);
            }
        }
        return restaurantVOsCopy;
    }

}

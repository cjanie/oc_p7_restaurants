package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantsViewModel extends ViewModel {

    // Use Cases
    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final GetSessionUseCase getSessionUseCase;

    // Restaurant List LiveData
    private final MutableLiveData<List<RestaurantVO>> restaurants;

    // For stream data (Observable)
    private Disposable disposable;


    // Constructor
    public RestaurantsViewModel(GetRestaurantsForListUseCase getRestaurantsForListUseCase) {
        this.getRestaurantsForListUseCase = getRestaurantsForListUseCase;
        this.restaurants = new MutableLiveData<>(new ArrayList<>());
        this.getSessionUseCase = new GetSessionUseCase(new SessionGatewayImpl());
    }

    // GET methods

    public LiveData<List<RestaurantVO>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        this.setRestaurants(
                this.getRestaurantsForListUseCase.getRestaurantsNearbyAsValueObject(new Geolocation(myLatitude, myLongitude), radius)
        );
        return this.restaurants;
    }

    private void setRestaurants(Observable<List<RestaurantVO>> observableWithRestaurants) {
        this.disposable = observableWithRestaurants
                .subscribeWith(new DisposableObserver<List<RestaurantVO>>() {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<RestaurantVO> restaurants) {
                        RestaurantsViewModel.this.restaurants.postValue(restaurants);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(this.disposable != null && !this.disposable.isDisposed())
            this.disposable.dispose();
    }
}

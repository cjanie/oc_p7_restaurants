package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.usecases.GetSession;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.repositories.DistanceRepository;
import com.android.go4lunch.repositories.RestaurantRepository;
import com.android.go4lunch.usecases.GetRestaurantsForList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantsViewModel extends ViewModel {

    // Use Cases
    private final GetRestaurantsForList getRestaurantsForList;

    // Restaurant List LiveData
    private final MutableLiveData<List<RestaurantVO>> restaurants;

    // For stream data (Observable)
    private Disposable disposable;


    // Constructor
    public RestaurantsViewModel() {
        this.getRestaurantsForList = new GetRestaurantsForList(
                new RestaurantRepository(),
                new RealTimeProvider(),
                new RealDateProvider(),
                new DistanceRepository());
        this.restaurants = new MutableLiveData<>(new ArrayList<>());
    }

    // GET methods

    public LiveData<List<RestaurantVO>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        this.setRestaurants(
                this.getRestaurantsForList.getRestaurantsNearbyAsValueObjectWithDistance(new Geolocation(myLatitude, myLongitude), radius)
        );
        return this.restaurants;
    }

    private void setRestaurants(Observable<List<RestaurantVO>> observableWithRestaurants) {
        this.disposable = observableWithRestaurants
                .subscribeWith(new DisposableObserver<List<RestaurantVO>>() {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<RestaurantVO> restaurants) {
                        RestaurantsViewModel.this.restaurants.setValue(restaurants);
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
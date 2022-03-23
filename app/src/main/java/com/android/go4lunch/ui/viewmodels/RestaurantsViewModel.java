package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;
import com.android.go4lunch.usecases.decorators.TimeInfoDecorator;
import com.android.go4lunch.usecases.models.RestaurantModel;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantsViewModel extends ViewModel {

    // Use Cases
    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    // Dependencies
    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    // Restaurant List LiveData
    private final MutableLiveData<List<RestaurantModel>> restaurants;

    // For stream data (Observable)
    private Disposable disposable;


    // Constructor
    public RestaurantsViewModel(
            GetRestaurantsForListUseCase getRestaurantsForListUseCase,
            GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider) {
        this.getRestaurantsForListUseCase = getRestaurantsForListUseCase;
        this.getRestaurantVisitorsUseCase = getRestaurantVisitorsUseCase;
        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;
        this.restaurants = new MutableLiveData<>(new ArrayList<>());
    }

    // GET methods

    public LiveData<List<RestaurantModel>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        List<Restaurant> restaurantsResults = new ArrayList<>();
        this.getRestaurantsForListUseCase.handle(new Geolocation(myLatitude, myLongitude), radius)
                .subscribe(restaurantsResults::addAll);
        List<RestaurantModel> restaurantModels = new ArrayList<>();
        if(!restaurantsResults.isEmpty()) {
            for(Restaurant r: restaurantsResults) {
                List<Workmate> visitorsResults = new ArrayList<>();
                this.getRestaurantVisitorsUseCase.handle(r.getId()).subscribe(visitorsResults::addAll);
                RestaurantModel restaurantModel = new RestaurantModel(
                        r,
                        this.timeProvider,
                        this.dateProvider,
                        100L,
                        visitorsResults);
                restaurantModels.add(restaurantModel);
            }
        }
        this.setRestaurants(
                Observable.just(restaurantModels)
        );
        return this.restaurants;
    }

    private void setRestaurants(Observable<List<RestaurantModel>> observableWithRestaurants) {
        this.disposable = observableWithRestaurants
                .subscribeWith(new DisposableObserver<List<RestaurantModel>>() {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<RestaurantModel> restaurants) {

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

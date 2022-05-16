package com.android.go4lunch.ui.viewmodels;

import android.util.Log;

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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;

public class RestaurantsViewModel extends ViewModel {

    private final String TAG = "RESTAURANTS VIEW MODEL";

    // Use Cases
    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    // Dependencies
    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    private Observable<List<RestaurantModel>> restaurantModelsObservable;

    // Restaurant List LiveData
    private final MutableLiveData<List<RestaurantModel>> restaurants;

    // Data observer
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
        this.getRestaurantsForListUseCase.handle(myLatitude, myLongitude, radius)
                .subscribe(restaurantsResults::addAll);
        Log.d(TAG, "-- getRestaurants -- restaurants size: " + restaurantsResults.size());
        List<RestaurantModel> restaurantModels = new ArrayList<>();
        if(!restaurantsResults.isEmpty()) {
            for(Restaurant r: restaurantsResults) {
                List<String> visitorsResults = new ArrayList<>();
                this.getRestaurantVisitorsUseCase.handle(r.getId()).subscribe(visitorsResults::addAll);
                Log.d(TAG, "-- getRestaurants -- visitors size: " + visitorsResults.size());
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
        ////////
        this.updateRestaurantModelsObservable(myLatitude, myLongitude, radius);
        this.updateRestaurants();
        ////////
        return this.restaurants;
    }

    public void updateRestaurants() {

        this.restaurantModelsObservable.subscribe(restaurantModels -> {
            Log.d(TAG, "-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% update Restaurants -- restaurantsModels size: " + restaurantModels.size());
            restaurants.postValue(restaurantModels);

        });

    }

    private Observable<List<RestaurantModel>> getRestaurantsAsModelsObservable(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurantsForListUseCase.handle(myLatitude, myLongitude, radius).flatMap(
                restaurants -> {
                    Log.d(TAG, "-- getRestaurantsAsModelsObservable -- restaurants size: " + restaurants.size());

                    return Observable.fromIterable(restaurants)
                            .flatMap(
                                    restaurant ->
                                            formatRestaurantToModelObservable(restaurant)
                            ).toList().toObservable();
                }
        );
    }

    public Observable<RestaurantModel> formatRestaurantToModelObservable(Restaurant restaurant) {
        return this.getRestaurantVisitorsUseCase.handle(restaurant.getId()).map(visitors -> {
            Log.d(TAG, "-- getRestaurants -- visitors size: " + visitors.size());

            RestaurantModel restaurantModel = new RestaurantModel(
                    restaurant,
                    this.timeProvider,
                    this.dateProvider,
                    100L,
                    visitors
            );
            return restaurantModel;
        });
    }

    private void updateRestaurantModelsObservable(Double myLatitude, Double myLongitude, int radius) {
        this.restaurantModelsObservable = this.getRestaurantsAsModelsObservable(myLatitude, myLongitude, radius);
    }

    private void setRestaurants(Observable<List<RestaurantModel>> observableWithRestaurants) {
        observableWithRestaurants.subscribe(restaurantModels -> {
            Log.d(TAG, "-- setRestaurants --: restaurantsmodels size: " + restaurantModels.size());
           this.restaurants.postValue(restaurantModels);
        });
        /*
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

         */
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(this.disposable != null && !this.disposable.isDisposed())
            this.disposable.dispose();
    }
}

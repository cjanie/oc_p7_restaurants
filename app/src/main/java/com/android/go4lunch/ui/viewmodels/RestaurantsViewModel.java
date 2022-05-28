package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsForListUseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantsViewModel extends ViewModel {

    private final String TAG = "RESTAURANTS VIEW MODEL";

    // Use Cases
    private final GetRestaurantsForListUseCase getRestaurantsForListUseCase;

    private final GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;

    // Dependencies
    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    private Observable<List<RestaurantValueObject>> restaurantModelsObservable;

    // List LiveData
    private final MutableLiveData<List<RestaurantValueObject>> restaurantsLiveData;

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

        this.restaurantsLiveData = new MutableLiveData<>(new ArrayList<>());
    }


/*
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

 */
/*
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
*/


    // Getter for the view the model livedata that the activity listens
    public LiveData<List<RestaurantValueObject>> getRestaurantsLiveData() {
        return this.restaurantsLiveData;
    }

    // Action
    public void fetchRestaurantsObservableToUpdateLiveData(Double myLatitude, Double myLongitude, int radius) {
        this.getRestaurantsForListUseCase.handle(myLatitude, myLongitude, radius)
                .subscribe(restaurants -> {
                    List<RestaurantValueObject> restaurantValueObjects = new ArrayList<>();
                    if(!restaurants.isEmpty()) {
                        for(Restaurant restaurant: restaurants) {
                            RestaurantValueObject restaurantValueObject = new RestaurantValueObject(restaurant, this.timeProvider, this.dateProvider, 100L, new ArrayList<>());
                            restaurantValueObjects.add(restaurantValueObject);
                        }
                    }
                    this.restaurantsLiveData.postValue(restaurantValueObjects);
                });
    }



}

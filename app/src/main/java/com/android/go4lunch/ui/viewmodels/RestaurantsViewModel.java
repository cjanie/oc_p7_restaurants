package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.SearchRestaurantUseCase;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.ui.loader.LoadingException;
import com.android.go4lunch.ui.presenters.RestaurantListPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class RestaurantsViewModel extends ViewModel {

    // Use Cases
    private final GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    private final SearchRestaurantUseCase searchRestaurantUseCase;

    private final GetNumberOfLikesPerRestaurantUseCase likeUseCase;

    private final GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase;

    private final TimeProvider timeProvider;

    private final DateProvider dateProvider;

    // List Presenter
    private final RestaurantListPresenter presenter;

    // LiveData
    // LIST
    private final MutableLiveData<List<RestaurantValueObject>> restaurants;

    // SEARCH RESULT
    private final MutableLiveData<RestaurantValueObject> searchResult;

    // LOADER
    private final MutableLiveData<Boolean> isLoading;


    // Constructor
    public RestaurantsViewModel(
            GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase,
            SearchRestaurantUseCase searchRestaurantUseCase,
            GetNumberOfLikesPerRestaurantUseCase likeUseCase,
            GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase,
            TimeProvider timeProvider,
            DateProvider dateProvider) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
        this.likeUseCase = likeUseCase;
        this.distanceUseCase = distanceUseCase;

        this.timeProvider = timeProvider;
        this.dateProvider = dateProvider;

        this.presenter = new RestaurantListPresenter(this.likeUseCase, this.distanceUseCase);

        this.restaurants = new MutableLiveData<>(new ArrayList<>());
        this.searchResult = new MutableLiveData<>();
        this.isLoading = new MutableLiveData<>(true);
    }

    // Getter for the view the model livedata that the activity listens
    public LiveData<List<RestaurantValueObject>> getRestaurants() {
        return this.restaurants;
    }

    public LiveData<RestaurantValueObject> getSearchResult() {
        return this.searchResult;
    }

    public LiveData<Boolean> isLoading() {
        return this.isLoading;
    }

    // Actions
    // List
    public void fetchRestaurantsObservableToUpdateLiveData(Double myLatitude, Double myLongitude, int radius) {
        this.isLoading.postValue(true);
        Observable<List<RestaurantValueObject>> restaurantsObservable = this.getRestaurantsNearbyUseCase.handle(myLatitude, myLongitude, radius);
        restaurantsObservable = this.presenter.updateRestaurantsWithDistance(restaurantsObservable, myLatitude, myLongitude);
        restaurantsObservable = this.presenter.updateRestaurantsWithLikesCount(restaurantsObservable);
        restaurantsObservable = this.presenter.updateRestaurantsWithTimeInfo(restaurantsObservable, this.timeProvider, this.dateProvider);
        restaurantsObservable.subscribe(restaurants -> {
                    this.restaurants.postValue(restaurants);
                    this.isLoading.postValue(false);// TODO Loading
                },
                error -> {
                    this.isLoading.postValue(false);// TODO Loading
                    error.printStackTrace();
                    throw new LoadingException(error.getClass() + " " + error.getMessage());
                },
                () ->
                        this.isLoading.postValue(false)//// TODO Loading
        );
    }

    // Search
    public void fetchSearchResultToUpdateLiveData(String restaurantId, Double myLatitude, Double myLongitude) {
        this.isLoading.postValue(true);
        Observable<RestaurantValueObject> restaurant = this.searchRestaurantUseCase.handle(restaurantId);
        restaurant = this.presenter.updateRestaurantWithLikesCount(restaurant);

        restaurant = this.presenter.updateRestaurantWithDistance(restaurant, myLatitude, myLongitude);

        restaurant = this.presenter.updateRestaurantWithTimeInfo(restaurant, this.timeProvider, this.dateProvider);
        restaurant.subscribe(
                r -> {
                    this.searchResult.postValue(r);
                    this.isLoading.postValue(false);
                },
                error -> {
                    this.isLoading.postValue(false);
                    error.printStackTrace();
                    throw new LoadingException(error.getClass() + " " + error.getMessage());
                },
                () ->
                    this.isLoading.postValue(false)

        );

    }

}

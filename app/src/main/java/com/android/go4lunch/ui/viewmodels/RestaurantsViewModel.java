package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.gateways_impl.InMemorySelectionGateway;
import com.android.go4lunch.gateways_impl.InMemoryHistoricOfSelectionsGateway;
import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.usecases.GetSession;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Geolocation;
import com.android.go4lunch.usecases.GetRestaurantsForList;
import com.android.go4lunch.usecases.ToggleSelection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantsViewModel extends ViewModel {

    // Use Cases
    private final GetRestaurantsForList getRestaurantsForList;

    private final GetSession getSession;

    // Action
    private final ToggleSelection toggleSelection;

    // Restaurant List LiveData
    private final MutableLiveData<List<RestaurantVO>> restaurants;

    // For stream data (Observable)
    private Disposable disposable;


    // Constructor
    public RestaurantsViewModel(GetRestaurantsForList getRestaurantsForList) {
        this.getRestaurantsForList = getRestaurantsForList;
        this.restaurants = new MutableLiveData<>(new ArrayList<>());
        this.getSession = new GetSession(new SessionGatewayImpl());

        // Select
        this.toggleSelection = new ToggleSelection(
                new InMemorySelectionGateway(),
                this.getSession,
                new InMemoryHistoricOfSelectionsGateway()
                );
    }

    public void toggleSelection(Restaurant restaurant) throws NoWorkmateForSessionException {
        this.toggleSelection.toggle(restaurant);
    }


    // GET methods

    public LiveData<List<RestaurantVO>> getRestaurants(Double myLatitude, Double myLongitude, int radius) {
        this.setRestaurants(
                this.getRestaurantsForList.getRestaurantsWithSelections(new Geolocation(myLatitude, myLongitude), radius)
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

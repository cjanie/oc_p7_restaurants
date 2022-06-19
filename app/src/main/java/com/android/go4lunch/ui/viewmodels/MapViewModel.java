package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class MapViewModel extends ViewModel {

    private String TAG = "MAP VIEW MODEL";

    // Use Case
    private GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    // LiveData
    private final MutableLiveData<List<MarkerOptions>> markersLiveData;

    // Constructor
    public MapViewModel(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.markersLiveData = new MutableLiveData<>(new ArrayList<>());
    }

    // Getter for the view the model livedata that the activity listens
    public LiveData<List<MarkerOptions>> getRestaurantsMarkersLiveData() {
        return this.markersLiveData;
    }

    // View model Action that updates the view model livedata
    public void fetchRestaurantsToUpdateRestaurantsMarkersLiveData(Double myLatitude, Double myLongitude, int radius) {
        this.getRestaurantsNearbyUseCase.handle(myLatitude, myLongitude, radius)
                .subscribeOn(Schedulers.io())
                .subscribe(restaurants -> {
                    List<MarkerOptions> markersOptions = new ArrayList<>();
                    if(!restaurants.isEmpty()) {
                        for(RestaurantValueObject restaurant: restaurants) {
                            if(restaurant.getRestaurant().getGeolocation() != null) {
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(new LatLng(
                                                restaurant.getRestaurant().getGeolocation().getLatitude(),
                                                restaurant.getRestaurant().getGeolocation().getLongitude())
                                        )
                                        .title(restaurant.getRestaurant().getName())
                                        .snippet(restaurant.getRestaurant().getAddress())
                                        .icon(restaurant.getVisitorsCount() > 0 ?
                                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                                                :
                                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                                markersOptions.add(markerOptions);
                            }
                        }
                    }
                    markersLiveData.postValue(markersOptions);
                }, Throwable::printStackTrace);
    }

}

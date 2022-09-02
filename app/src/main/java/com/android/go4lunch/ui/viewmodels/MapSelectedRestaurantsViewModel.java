package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.usecases.restaurant.FilterSelectedRestaurantsUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.ui.loader.LoadingException;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSelectedRestaurantsViewModel extends ViewModel {

    private FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase;

    private final MutableLiveData<List<MarkerOptions>> restaurantsMarkers;

    private final MutableLiveData<Map<String, RestaurantValueObject>> restaurantsMarkersMap;

    public MapSelectedRestaurantsViewModel(FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase) {
        this.filterSelectedRestaurantsUseCase = filterSelectedRestaurantsUseCase;
        this.restaurantsMarkers = new MutableLiveData<>(new ArrayList<>());
        this.restaurantsMarkersMap = new MutableLiveData<>(new HashMap<>());
    }

    public LiveData<List<MarkerOptions>> getRestaurantsMarkers() {
        return this.restaurantsMarkers;
    }

    public LiveData<Map<String, RestaurantValueObject>> getRestaurantsMarkersMap() {
        return this.restaurantsMarkersMap;
    }

    public void fetchRestaurantsToUpdateRestaurantsMarkersLiveData(Double myLatitude, Double myLongitude, int radius) {
        this.filterSelectedRestaurantsUseCase.handle(myLatitude, myLongitude, radius)
                .subscribe(
                        restaurants -> {
                            List<MarkerOptions> markers = new ArrayList<>();
                            Map<String, RestaurantValueObject> markersRestaurants = new HashMap<>();
                            if(!restaurants.isEmpty()) {
                                for(RestaurantValueObject restaurant: restaurants) {
                                    MarkerOptions marker = createMarker(restaurant);
                                    markers.add(marker);
                                    markersRestaurants.put(marker.getTitle(), restaurant);
                                }
                            }
                            restaurantsMarkers.postValue(markers);
                            restaurantsMarkersMap.postValue(markersRestaurants);
                        },
                        error -> {
                            error.printStackTrace();
                            throw new LoadingException(error.getMessage());
                        });
    }

    private MarkerOptions createMarker(RestaurantValueObject restaurant) {
        return new MarkerOptions()
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
    }

}

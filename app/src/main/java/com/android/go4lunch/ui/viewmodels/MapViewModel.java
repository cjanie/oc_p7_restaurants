package com.android.go4lunch.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.SearchRestaurantUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.ui.loader.LoadingException;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewModel extends ViewModel {

    // Use Case
    private GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    private SearchRestaurantUseCase searchRestaurantUseCase;

    // LiveData
    // MARKERS NEARBY
    private final MutableLiveData<List<MarkerOptions>> restaurantsMarkers;

    private final MutableLiveData<Map<String, RestaurantValueObject>> restaurantsMarkersMap;

    // SEARCH RESULT MARKER
    private final MutableLiveData<MarkerOptions> searchResultMarker;

    private final MutableLiveData<Map<String, RestaurantValueObject>> searchResultMarkerMap;

    // Constructor
    public MapViewModel(GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase, SearchRestaurantUseCase searchRestaurantUseCase) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
        this.restaurantsMarkers = new MutableLiveData<>(new ArrayList<>());
        this.restaurantsMarkersMap = new MutableLiveData<>(new HashMap<>());
        this.searchResultMarker = new MutableLiveData<>();
        this.searchResultMarkerMap = new MutableLiveData<>(new HashMap<>());
    }

    // Getter for the view the model livedata that the activity listens
    public LiveData<List<MarkerOptions>> getRestaurantsMarkers() {
        return this.restaurantsMarkers;
    }

    public LiveData<Map<String, RestaurantValueObject>> getMarkersRestaurantsMap() {
        return this.restaurantsMarkersMap;
    }

    public LiveData<MarkerOptions> getSearchResultMarker() {
        return this.searchResultMarker;
    }

    public LiveData<Map<String, RestaurantValueObject>> getSearchResultMarkerMap() {
        return this.searchResultMarkerMap;
    }

    // View model Actions that updates the view model livedata
    public void fetchRestaurantsToUpdateRestaurantsMarkersLiveData(Double myLatitude, Double myLongitude, int radius) {
        this.getRestaurantsNearbyUseCase.handle(myLatitude, myLongitude, radius)
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

    public void fetchSearchResultToUpdateData(String restaurantId) {
        this.searchRestaurantUseCase.handle(restaurantId)
                .subscribe(
                        restaurant -> {
                            MarkerOptions marker = createMarker(restaurant);
                            this.searchResultMarker.postValue(marker);
                            Map<String, RestaurantValueObject> searchResultMap = new HashMap<>();
                            searchResultMap.put(marker.getTitle(), restaurant);
                            this.searchResultMarkerMap.postValue(searchResultMap);
                        },
                        error -> {
                            error.printStackTrace();
                            throw new LoadingException(error.getMessage());
                        }
                );
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

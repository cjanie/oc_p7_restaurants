package com.android.go4lunch.data.apiGoogleMaps.repositories;

import android.util.Log;

import com.android.go4lunch.data.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.place.DetailsResponseRoot;
import com.android.go4lunch.data.apiGoogleMaps.deserializers.place.Result;
import com.android.go4lunch.data.apiGoogleMaps.exceptions.DetailRequestException;
import com.android.go4lunch.data.apiGoogleMaps.exceptions.NearbySearchRequestException;
import com.android.go4lunch.data.apiGoogleMaps.factories.PlanningFactory;
import com.android.go4lunch.data.apiGoogleMaps.entities.RestaurantDTO;
import com.android.go4lunch.data.apiGoogleMaps.requests.DetailsRequest;
import com.android.go4lunch.data.apiGoogleMaps.requests.NearbySearchRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class RestaurantRepository {

    private String TAG = "RESTAURANT REPOSITORY";

    private NearbySearchRequest nearbySearchRequest;

    private DetailsRequest detailsRequest;

    public RestaurantRepository(GoogleMapsHttpClientProvider httpClientProvider) {
        this.nearbySearchRequest = httpClientProvider.getRetrofit().create(NearbySearchRequest.class);
        this.detailsRequest = httpClientProvider.getRetrofit().create(DetailsRequest.class);
    }

    public Observable<List<RestaurantDTO>> getRestaurantsNearby(Double latitude, Double longitude, int radius) {
        return this.getRestaurantsNearbyIds(latitude, longitude, radius)
                .flatMap(restaurantIds ->
                        Observable.fromIterable(restaurantIds)
                                .flatMap(restaurantId ->
                                        getRestaurantWithDetailsById(restaurantId)
                                )
                                .toList().toObservable()
                );
    }

    public Observable<RestaurantDTO> getRestaurantById(String restaurantId) {
        return this.getRestaurantWithDetailsById(restaurantId);
    }

    private Observable<List<String>> getRestaurantsNearbyIds(Double latitude, Double longitude, int radius) {
        // Get data from request
        return this.nearbySearchRequest.getData(
                latitude.toString() + "," + longitude.toString(),
                radius,
                GoogleMapsRequestConfig.NEARBY_SEARCH_TYPE_PARAM,
                GoogleMapsRequestConfig.NEARBY_SEARCH_FIELDS_PARAM,
                GoogleMapsRequestConfig.API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError((error) -> {
                    Log.e(TAG, error.getMessage());
                    throw new NearbySearchRequestException();
                })
                .map(nearbySearchResponseRoot -> {

                    List<String> restaurantsIds = new ArrayList<>();

                    // Get the Result list from response
                    List<Result> results = nearbySearchResponseRoot.getResults();
                    if(!results.isEmpty()) {
                        for(Result result: results) {
                            restaurantsIds.add(result.getPlaceId());
                        }
                    }
                    return restaurantsIds;
                });
    }

    private Observable<RestaurantDTO> getRestaurantWithDetailsById(String restaurantId) {

        return this.detailsRequest.getData(
                restaurantId,
                GoogleMapsRequestConfig.DETAILS_FIELDS_PARAM,
                GoogleMapsRequestConfig.API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError((error) -> {
                    Log.e(TAG, error.getMessage());
                    throw new DetailRequestException();
                })
                .map(detailsResponseRoot ->
                        this.buildRestaurantDTOWithDetails(detailsResponseRoot)
                );
    }


    private RestaurantDTO buildRestaurantDTOWithDetails(DetailsResponseRoot detailsResponseRoot) {
        RestaurantDTO restaurant = new RestaurantDTO();

        Result result = detailsResponseRoot.getResult();
        if(result != null) {
            restaurant.setId(result.getPlaceId());
            restaurant.setName(result.getName());
            restaurant.setLatitude(result.getGeometry().getLocation().getLat());
            restaurant.setLongitude(result.getGeometry().getLocation().getLng());

            if(result.getPhotos() != null && !result.getPhotos().isEmpty()) {
                // url for Google place api Photo request
                String url = GoogleMapsRequestConfig.BASE_URL
                        + GoogleMapsRequestConfig.PHOTO_ENDPOINT
                        + "?maxwidth=" + GoogleMapsRequestConfig.PHOTO_MAXWIDTH_PARAM
                        + "&photo_reference=" + result.getPhotos().get(0).getPhotoReference()
                        + "&key=" + GoogleMapsRequestConfig.API_KEY;
                restaurant.setPhotoUrl(url);
            }

            restaurant.setAddress(result.getAddress().split(",")[0]);

            // Set opening hours
            if(result.getOpeningHours() != null && result.getOpeningHours().getPeriods() != null) {
                PlanningFactory planningFactory = new PlanningFactory();
                restaurant.setPlanning(planningFactory.convertPeriods(result.getOpeningHours().getPeriods()));
            }
        }

        return restaurant;
    }
}

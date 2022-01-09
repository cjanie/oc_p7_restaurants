package com.android.go4lunch.apiGoogleMaps;

import com.android.go4lunch.apiGoogleMaps.deserialized_entities.Result;
import com.android.go4lunch.apiGoogleMaps.entities.PlanningDeserializer;
import com.android.go4lunch.apiGoogleMaps.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantStream {

    public static Observable<List<Restaurant>> getRestaurantsNearby(Double latitude, Double longitude, int radius) {
        // Instanciate the Restaurant list for the return
        List<Restaurant> restaurants = new ArrayList<>();
        // Make request to get data
        NearbySearchService nearbySearchService = NearbySearchService.retrofit.create(NearbySearchService.class);
        return nearbySearchService.getData(
                latitude.toString() + "," + longitude.toString(),
                radius,
                GoogleMapsRequestConfig.NEARBY_SEARCH_TYPE_PARAM,
                GoogleMapsRequestConfig.API_KEY
        )
                // operator to execute request in a dedicated thread (Schedulers.io)
                .subscribeOn(Schedulers.io())
                // operator for all subscribers on main thread to listen
                .observeOn(AndroidSchedulers.mainThread())
                // operator for timeout error
                .timeout(10, TimeUnit.SECONDS)
                // Make a Restaurant list from response
                .map(nearbySearchResponseRoot -> {
                    // Get the Result list from response
                    List<Result> results = nearbySearchResponseRoot.getResults();
                    if(!results.isEmpty()) {
                        for(Result result: results) {

                            // create Restaurant from Result
                            Restaurant restaurant = new Restaurant();
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

                            // fill Restaurant list
                            restaurants.add(restaurant);
                        }
                    }
                    return restaurants;
                });
    }

    public static Observable<List<Restaurant>> getRestaurantsNearbyWithDetails(Double latitude, Double longitude, int radius) {
        return getRestaurantsNearby(latitude, longitude, radius)
                .flatMap(restaurants ->
                        Observable.fromIterable(restaurants)
                                .flatMap(restaurant ->
                                        detailRestaurantWithMoreProperties(restaurant)
                                )
                                .toList().toObservable()
                );
    }


    private static Observable<Restaurant> detailRestaurantWithMoreProperties(Restaurant restaurant) {
        DetailsService detailsService = DetailsService.retrofit.create(DetailsService.class);
        return detailsService.getData(
                restaurant.getId(),
                GoogleMapsRequestConfig.DETAILS_FIELDS_PARAM,
                GoogleMapsRequestConfig.API_KEY
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS)
                // set restaurant properties from details service response and return it
                .map(detailsResponseRoot -> {
                    Result result = detailsResponseRoot.getResult();
                    if(result != null) {
                        restaurant.setAddress(result.getAddress().split(",")[0]);

                        // Set opening hours
                        if(result.getOpeningHours() != null && result.getOpeningHours().getPeriods() != null) {
                            PlanningDeserializer planningDeserializer = new PlanningDeserializer();
                            restaurant.setPlanning(planningDeserializer.convertPeriods(result.getOpeningHours().getPeriods()));
                        }
                    }

                    return restaurant;
                });
    }

}

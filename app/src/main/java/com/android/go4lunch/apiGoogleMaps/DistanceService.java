package com.android.go4lunch.apiGoogleMaps;

import com.android.go4lunch.apiGoogleMaps.deserialized_entities.distance.DistanceResponseRoot;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DistanceService extends RetrofitForGoogleMapsApiService {

    @GET(GoogleMapsRequestConfig.DISTANCE_MATRIX_ENDPOINT)
    Observable<DistanceResponseRoot> getData(
            @Query("destinations") List<String> destinations,
            @Query("origins") List<String> origins,
            @Query("key") String key
            );
}

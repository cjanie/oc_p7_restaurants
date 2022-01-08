package com.android.go4lunch.httpclient;

import com.android.go4lunch.httpclient.deserialized_entities.NearbySearchResponseRoot;

import io.reactivex.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NearbySearchService extends RetrofitForGoogleMapsApiService {

    @GET(GoogleMapsRequestConfig.NEARBY_SEARCH_END_POINT)
    Observable<NearbySearchResponseRoot> getData(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String key
    );
}

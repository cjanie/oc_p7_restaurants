package com.android.go4lunch.apiGoogleMaps;

import com.android.go4lunch.apiGoogleMaps.deserialized_entities.PhotoResponseRoot;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoService extends RetrofitForGoogleMapsApiService {

    @GET(GoogleMapsRequestConfig.PHOTO_ENDPOINT)
    Observable<PhotoResponseRoot> getData(
        @Query("maxwidth") int maxWidth,
        @Query("photo_reference") String photoReference,
        @Query("key") String key
    );
}

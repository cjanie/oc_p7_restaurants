package com.android.go4lunch.apiGoogleMaps;

import com.android.go4lunch.exceptions.NullDistanceResponseException;
import com.android.go4lunch.apiGoogleMaps.deserialized_entities.distance.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DistanceStream {

    public static Observable<Integer> getDistanceInMeter(
            String destination,
            String origin
    ) throws NullDistanceResponseException {
        // Instanciate service
        DistanceService distanceService = DistanceService.retrofit.create(DistanceService.class);
        // Prepare parameters of the request
        List<String> destinations = new ArrayList<>();
        destinations.add(destination);
        List<String> origins = new ArrayList<>();
        origins.add(origin);
        // return async response from request
        return distanceService.getData(
                destinations,
                origins,
                GoogleMapsRequestConfig.API_KEY
        )
                // operator to execute request in a dedicated thread (Schedulers.io)
                .subscribeOn(Schedulers.io())
                // operator for all subscribers on main thread to listen
                .observeOn(AndroidSchedulers.mainThread())
                // operator for timeout error
                .timeout(10, TimeUnit.SECONDS)
                .map(root -> {
                    if(root == null || root.getRows() == null)
                        throw new NullDistanceResponseException();

                    Integer distance = null;
                    if(!root.getRows().isEmpty()) {
                        List<Element> elements = root.getRows().get(0).getElements();

                        if(elements == null)
                            throw new NullDistanceResponseException();

                        if(!elements.isEmpty()) {
                            if(elements.get(0).getDistance() == null)
                                throw new NullDistanceResponseException();
                            distance = elements.get(0).getDistance().getValue();
                        }
                    }
                    return distance;
                });
    }

}

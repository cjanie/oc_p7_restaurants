package com.android.go4lunch.businesslogic.usecases.restaurant;

import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class FilterSelectedRestaurantsUseCase {

    private GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;

    private BehaviorSubject<List<RestaurantValueObject>> selectedRestaurantsSubject;

    public FilterSelectedRestaurantsUseCase(
            GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase
    ) {
        this.getRestaurantsNearbyUseCase = getRestaurantsNearbyUseCase;
        this.selectedRestaurantsSubject = BehaviorSubject.create();
    }

    public Observable<List<RestaurantValueObject>> handle(Double myLatitude, Double myLongitude, int radius) {
        return this.getRestaurantsNearbyUseCase.handle(myLatitude, myLongitude, radius)
                .flatMap(restaurants -> {

                    if(!restaurants.isEmpty()) {
                        List<RestaurantValueObject> selectedRestaurants = new ArrayList<>();
                        for(RestaurantValueObject r: restaurants) {
                            if(r.getVisitorsCount() > 0) {
                                selectedRestaurants.add(r);
                            }
                        }
                        this.selectedRestaurantsSubject.onNext(selectedRestaurants);
                    }

                    return this.selectedRestaurantsSubject;
                });
    }
}

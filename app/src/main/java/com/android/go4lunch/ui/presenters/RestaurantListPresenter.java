package com.android.go4lunch.ui.presenters;

import com.android.go4lunch.businesslogic.entities.Geolocation;
import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.TimeProvider;

import java.util.List;

import io.reactivex.Observable;

public class RestaurantListPresenter {

    private GetNumberOfLikesPerRestaurantUseCase likeUseCase;

    private GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase;

    public RestaurantListPresenter(
            GetNumberOfLikesPerRestaurantUseCase likeUseCase,
            GetDistanceFromMyPositionToRestaurantUseCase distanceUseCase
    ) {
        this.likeUseCase = likeUseCase;
        this.distanceUseCase = distanceUseCase;
    }

    public Observable<List<RestaurantValueObject>> updateRestaurantsWithLikesCount(Observable<List<RestaurantValueObject>> restaurantsObservable) {
        return restaurantsObservable.map(restaurants -> {
            if(!restaurants.isEmpty()) {
                for(RestaurantValueObject r: restaurants) {
                    this.likeUseCase.handle(r.getRestaurant().getId()).subscribe(likesCount -> {
                        r.setLikesCount(likesCount);
                    });
                }
            }
            return restaurants;
        });
    }

    public Observable<List<RestaurantValueObject>> updateRestaurantsWithDistance(Observable<List<RestaurantValueObject>> restaurantsObservable, Double myLatitude, Double myLongitude) {
        Geolocation myPosition = new Geolocation(myLatitude, myLongitude);
        return restaurantsObservable.map(restaurants -> {
            if(!restaurants.isEmpty()) {
                for(RestaurantValueObject r: restaurants) {
                    this.distanceUseCase.handle(myPosition, r.getRestaurant().getGeolocation()).subscribe(distance -> {
                        r.setDistance(distance);
                    });
                }
            }
            return restaurants;
        });
    }

    public Observable<List<RestaurantValueObject>> updateRestaurantsWithTimeInfo(Observable<List<RestaurantValueObject>> restaurantsObservable, TimeProvider timeProvider, DateProvider dateProvider) {
        return restaurantsObservable.map(restaurants -> {
            if(!restaurants.isEmpty()) {
                for(RestaurantValueObject r: restaurants) {
                    r.setTimeInfo(timeProvider, dateProvider);
                    r.setOpenHoursToday(dateProvider);
                }
            }
            return restaurants;
        });
    }
}

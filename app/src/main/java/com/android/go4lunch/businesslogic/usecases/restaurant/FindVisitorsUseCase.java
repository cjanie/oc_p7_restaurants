package com.android.go4lunch.businesslogic.usecases.restaurant;

import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.valueobjects.RestaurantValueObject;

import java.util.List;

import io.reactivex.Observable;

public class FindVisitorsUseCase {

    protected VisitorGateway visitorGateway;

    protected Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }


    protected Observable<List<RestaurantValueObject>> updateRestaurantsWithVisitorsCount(
            List<RestaurantValueObject> restaurantVOs,
            Observable<List<Selection>> selectionsObservable
    ) {
        return selectionsObservable.map(selections -> {

            if(!restaurantVOs.isEmpty()) {
                for(RestaurantValueObject restaurantVO: restaurantVOs) {
                    int visitorsCount = this.getVisitorsCountByRestaurantId(selections, restaurantVO.getRestaurant().getId());
                    restaurantVO.setVisitorsCount(visitorsCount);
                }
            }
            return restaurantVOs;
        });
    }

    protected Observable<RestaurantValueObject> updateRestaurantWithVisitorCount(
            RestaurantValueObject restaurantVO
    ) {
       return this.getSelections().map(selections -> {
           if(!selections.isEmpty()) {
               int count = this.getVisitorsCountByRestaurantId(selections, restaurantVO.getRestaurant().getId());
               restaurantVO.setVisitorsCount(count);
           }
           return restaurantVO;
       });
    }

    private int getVisitorsCountByRestaurantId(List<Selection> selections, String restaurantId) {
        int count = 0;
        if (!selections.isEmpty()) {
            for (Selection selection : selections) {
                if (selection.getRestaurantId().equals(restaurantId)) {
                    count += 1;
                }
            }
        }
        return count;
    }


}

package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.gateways.SelectionQuery;
import com.android.go4lunch.usecases.models_vo.RestaurantVO;
import com.android.go4lunch.models.Selection;

import io.reactivex.Observable;


public class SelectionInfoDecoratorForRestaurant {

    private SelectionQuery selectionQuery;

    public SelectionInfoDecoratorForRestaurant(SelectionQuery selectionQuery) {
        this.selectionQuery = selectionQuery;
    }

    public Observable<RestaurantVO> decor(RestaurantVO restaurant) {
        return this.getSelectionsCount(restaurant).map(count -> {
            restaurant.setSelectionsCountInfo(count);
            return restaurant;
        });
    }

    private Observable<Integer> getSelectionsCount(RestaurantVO restaurant) {

        return this.selectionQuery.getSelections().map(selections -> {
            int count = 0;
            if(!selections.isEmpty()) {
                for(Selection selection: selections) {
                    if(selection.getRestaurant().equals(restaurant.getRestaurant())) {
                        count += 1;
                    }
                }
            }
            return count;
        });
    }
}

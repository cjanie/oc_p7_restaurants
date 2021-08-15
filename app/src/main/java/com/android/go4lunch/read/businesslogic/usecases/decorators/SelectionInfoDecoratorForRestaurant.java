package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;


public class SelectionInfoDecoratorForRestaurant implements Decorator<RestaurantVO> {

    private SelectionQuery selectionQuery;

    public SelectionInfoDecoratorForRestaurant(SelectionQuery selectionQuery) {
        this.selectionQuery = selectionQuery;
    }

    @Override
    public RestaurantVO decor(RestaurantVO restaurant) {
        restaurant.setSelectionsCountInfo(this.getSelectionsCount(restaurant));
        return restaurant;
    }

    private int getSelectionsCount(RestaurantVO restaurant) {
        int count = 0;
        for(Selection selection: this.selectionQuery.findAll()) {
            if(selection.getRestaurant().equals(restaurant.getRestaurant())) {
                count += 1;
            }
        }
        return count;
    }
}

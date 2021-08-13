package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;


public class SelectionInfoDecoratorForRestaurant extends SelectionInfoDecorator<RestaurantVO> {

    private RestaurantVO restaurantVO;


    public SelectionInfoDecoratorForRestaurant(SelectionQuery selectionQuery, RestaurantVO restaurantVO) {
        this.selectionQuery = selectionQuery;
        this.restaurantVO = restaurantVO;
    }

    @Override
    public RestaurantVO decor() {
        this.restaurantVO.setSelectionsCountInfo(this.getSelectionsCount());
        return this.restaurantVO;
    }

    private int getSelectionsCount() {
        int count = 0;
        for(Selection selection: this.selectionQuery.findAll()) {
            if(selection.getRestaurant().equals(this.restaurantVO.getRestaurant())) {
                count += 1;
            }
        }
        return count;
    }
}

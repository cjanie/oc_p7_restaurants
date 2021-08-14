package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.write.businesslogic.gateways.SelectionsCount;

public class Counter {

    private SelectionsCount selectionsCount;

    private Restaurant restaurant;

    public Counter(Restaurant restaurant, SelectionsCount selectionsCount) {
        this.restaurant = restaurant;
        this.selectionsCount = selectionsCount;
    }

    public int countSelections() {
        return this.selectionsCount.getCount(restaurant);
    }
}

package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.write.businesslogic.gateways.SelectionsCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryCounter implements SelectionsCount {

    List<Map<Restaurant, Integer>> list;

    public InMemoryCounter() {
        this.list = new ArrayList<>();
    }

    public void setList(List<Map<Restaurant, Integer>> list) {
        this.list = list;
    }

    @Override
    public int getCount(Restaurant restaurant) {
        int count = 0;
        for(Map map: this.list) {

            if(map.keySet().contains(restaurant)) {
                count = (int) map.get(restaurant);
            }
        }
        return count;
    }
}

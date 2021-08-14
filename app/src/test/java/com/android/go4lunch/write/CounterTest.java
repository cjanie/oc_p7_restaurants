package com.android.go4lunch.write;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.write.businesslogic.usecases.Counter;
import com.android.go4lunch.write.businesslogic.usecases.InMemoryCounter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterTest {

    private void checkMatchesData(int selectionsCount, int expected) {
        Restaurant restaurant = new Restaurant("Oa", "loc");
        InMemoryCounter inMemoryCounter = new InMemoryCounter();
        List<Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(restaurant, selectionsCount);
        list.add(map);
        inMemoryCounter.setList(list);
        assert(new Counter(restaurant, inMemoryCounter).countSelections() == expected);
    }


    @Test
    public void shouldReturn1IfRestaurantHasBeenSelectedOnce() {
        this.checkMatchesData( 1, 1);
    }

    @Test
    public void shouldReturn2IfRestaurantHasBeenSelectedTwice() {
        this.checkMatchesData(2, 2);
    }

    @Test
    public void shouldReturn0IfRestaurantHasNeverBeenSelected() {
        this.checkMatchesData(0, 0);
    }

}

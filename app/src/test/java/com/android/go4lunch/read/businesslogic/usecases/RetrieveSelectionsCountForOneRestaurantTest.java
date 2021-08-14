package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveSelectionsCountForOneRestaurantTest {

    private void checkMatchesData(int selectionsCount, int expected) {
        Restaurant restaurant = new Restaurant("Oa", "loc");
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();
        List<Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(restaurant, selectionsCount);
        list.add(map);
        inMemoryHistoricOfSelectionsRepository.setList(list);
        assert(new RetrieveSelectionsCountForOneRestaurant(restaurant, inMemoryHistoricOfSelectionsRepository).countSelections() == expected);
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

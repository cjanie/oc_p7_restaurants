package com.android.go4lunch.write;

import com.android.go4lunch.InMemoryHistoricOfSelectionsRepository;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.write.businesslogic.usecases.UpdateHistoric;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateHistoricTest {

    @Test
    public void shouldReturn1IfRestaurantHasIncrementFrom0() {
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();

        Restaurant restaurant = new Restaurant("AA", "OO");
        assert(inMemoryHistoricOfSelectionsRepository.getCount(restaurant) == 0);

        new UpdateHistoric(inMemoryHistoricOfSelectionsRepository, restaurant).handle();
        assert(inMemoryHistoricOfSelectionsRepository.getCount(restaurant) == 1);
    }

    @Test
    public void shouldReturn2IfRestaurantHasIncrementFrom1() {
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();

        Restaurant restaurant = new Restaurant("AA", "OO");

        List <Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(restaurant, 1);
        list.add(map);
        inMemoryHistoricOfSelectionsRepository.setList(list);
        assert(inMemoryHistoricOfSelectionsRepository.getCount(restaurant) == 1);

        new UpdateHistoric(inMemoryHistoricOfSelectionsRepository, restaurant).handle();
        assert(inMemoryHistoricOfSelectionsRepository.getCount(restaurant) == 2);
    }

    @Test
    public void shouldReturn3IfRestaurantHasIncrementFrom2() {
        InMemoryHistoricOfSelectionsRepository inMemoryHistoricOfSelectionsRepository = new InMemoryHistoricOfSelectionsRepository();

        Restaurant restaurant = new Restaurant("AA", "OO");

        List <Map<Restaurant, Integer>> list = new ArrayList<>();
        Map<Restaurant, Integer> map = new HashMap<>();
        map.put(restaurant, 2);
        list.add(map);
        inMemoryHistoricOfSelectionsRepository.setList(list);
        assert(inMemoryHistoricOfSelectionsRepository.getCount(restaurant) == 2);

        new UpdateHistoric(inMemoryHistoricOfSelectionsRepository, restaurant).handle();
        assert(inMemoryHistoricOfSelectionsRepository.getCount(restaurant) == 3);
    }
}

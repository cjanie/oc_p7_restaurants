package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.adapter.InMemoryRestaurantQuery;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.TimeInfo;

import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;

public class RestaurantVOTest {

    private void checkIfMatchesInfo(LocalTime open, LocalTime close, LocalTime now, Info info) {
        Restaurant restaurant = new Restaurant("r", "l");
        restaurant.setOpen(open);
        restaurant.setClose(close);

        InMemoryRestaurantQuery restaurantQuery = new InMemoryRestaurantQuery();
        restaurantQuery.setRestaurants(Arrays.asList(new Restaurant[] {restaurant}));
        DeterministicTimeProvider timeProvider = new DeterministicTimeProvider(now);

        assert(new TimeInfo(timeProvider, new RestaurantVO(restaurant)).getInfo().equals(info));


    }

    @Test
    public void shouldReturnCLOSEIfRestaurantIsClose() {

        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(23,0);
        LocalTime now = LocalTime.of(23,30);
        this.checkIfMatchesInfo(open, close, now, Info.CLOSE);
    }

    @Test
    public void shouldReturnOPENIfRestaurantIsOpen() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(23,0);
        LocalTime now = LocalTime.of(12,30);
        this.checkIfMatchesInfo(open, close, now, Info.OPEN);
    }

    @Test
    public void shouldReturnOPENINGSOONIfRestaurantIsOpenningSoon() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(23,0);
        LocalTime now = LocalTime.of(8,0);
        this.checkIfMatchesInfo(open, close, now, Info.OPENING_SOON);
    }
}

package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveRestaurants;
import com.android.go4lunch.read.businesslogic.usecases.enums.TimeInfo;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.decorators.TimeInfoDecorator;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;

public class TimeInfoDecoratorTest {

    @Before
    public void setUp() {

    }

    private void checkMatchesInfo(LocalTime open, LocalTime close, LocalTime now, TimeInfo expected) {
        Restaurant restaurant = new Restaurant("R1", "L");
        restaurant.setOpen(open);
        restaurant.setClose(close);
        TimeProvider timeProvider = new DeterministicTimeProvider(now);
        assert(new TimeInfoDecorator(timeProvider)
                .decor(new RestaurantVO(restaurant)).getTimeInfo().equals(expected));
    }

    @Test
    public void shouldShowThatRestaurantIsOpen() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(12, 0);
        this.checkMatchesInfo(open, close, now, TimeInfo.OPEN);
    }

    @Test
    public void shouldShowThatRestaurantIsClose() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(23, 0);
        this.checkMatchesInfo(open, close, now, TimeInfo.CLOSE);
    }

    @Test
    public void shouldShowCloseIf1AMAndRestaurantOpensAt9AM() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(1, 0);
        this.checkMatchesInfo(open, close, now, TimeInfo.CLOSE);
    }

    @Test
    public void shouldShowCloseSoon1HourBeforeClosing() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(19, 35);
        this.checkMatchesInfo(open, close, now, TimeInfo.CLOSING_SOON);
    }

    @Test
    public void shouldShowCloseSoon1HourExactlyBeforeClosing() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(19, 30);
        this.checkMatchesInfo(open, close, now, TimeInfo.CLOSING_SOON);
    }

    @Test
    public void shouldShowOpeningSoon1HourBeforeOpening() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(8, 35);
        this.checkMatchesInfo(open, close, now, TimeInfo.OPENING_SOON);
    }

    @Test
    public void shouldShowOpeningSoon1HourExactelyBeforeOpening() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(8, 0);
        this.checkMatchesInfo(open, close, now, TimeInfo.OPENING_SOON);
    }
}

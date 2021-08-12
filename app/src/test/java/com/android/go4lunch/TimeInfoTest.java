package com.android.go4lunch;

import com.android.go4lunch.read.adapter.DeterministicTimeProvider;
import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;
import com.android.go4lunch.read.businesslogic.usecases.Info;
import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.TimeInfo;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

public class TimeInfoTest {

    @Before
    public void setUp() {

    }

    private void checkMatchesInfo(LocalTime open, LocalTime close, LocalTime now, Info expected) {
        Restaurant restaurant = new Restaurant("R1", "L");
        restaurant.setOpen(open);
        restaurant.setClose(close);
        TimeProvider timeProvider = new DeterministicTimeProvider(now);
        assert(new TimeInfo(timeProvider, new RestaurantVO(restaurant)).getInfo().equals(expected));
    }

    @Test
    public void shouldShowThatRestaurantIsOpen() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(12, 0);
        this.checkMatchesInfo(open, close, now, Info.OPEN);
    }

    @Test
    public void shouldShowThatRestaurantIsClose() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(23, 0);
        this.checkMatchesInfo(open, close, now, Info.CLOSE);
    }

    @Test
    public void shouldShowCloseIf1AMAndRestaurantOpensAt9AM() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(1, 0);
        this.checkMatchesInfo(open, close, now, Info.CLOSE);
    }

    @Test
    public void shouldShowCloseSoon1HourBeforeClosing() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(19, 35);
        this.checkMatchesInfo(open, close, now, Info.CLOSING_SOON);
    }

    @Test
    public void shouldShowCloseSoon1HourExactlyBeforeClosing() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(19, 30);
        this.checkMatchesInfo(open, close, now, Info.CLOSING_SOON);
    }

    @Test
    public void shouldShowOpeningSoon1HourBeforeOpening() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(8, 35);
        this.checkMatchesInfo(open, close, now, Info.OPENING_SOON);
    }

    @Test
    public void shouldShowOpeningSoon1HourExactelyBeforeOpening() {
        LocalTime open = LocalTime.of(9,0);
        LocalTime close = LocalTime.of(20, 30);
        LocalTime now = LocalTime.of(8, 0);
        this.checkMatchesInfo(open, close, now, Info.OPENING_SOON);
    }
}

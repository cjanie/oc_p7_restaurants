package com.android.go4lunch.deserializer;

import com.android.go4lunch.apiGoogleMaps.deserialized_entities.OpenClose;
import com.android.go4lunch.apiGoogleMaps.deserialized_entities.Period;
import com.android.go4lunch.apiGoogleMaps.entities.PlanningDeserializer;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PlanningDeserializerTest {

    private PlanningDeserializer planningDeserializer;

    private List<Period> periods;

    // Result under test
    private Map<Integer, Map<String, LocalTime>> actual;

    @Before
    public void setUp() {
        this.planningDeserializer = new PlanningDeserializer();
        this.periods = new ArrayList<>();

        // Planning on monday
        Period periodDay1 = new Period();

        OpenClose openDay1 = new OpenClose();
        openDay1.setDay(1);
        openDay1.setTime("0800");

        OpenClose closeDay1 = new OpenClose();
        closeDay1.setDay(1);
        closeDay1.setTime("1830");

        periodDay1.setOpen(openDay1);
        periodDay1.setClose(closeDay1);

        periods.add(periodDay1);

        // Planning on tuesday
        Period periodDay2 = new Period();

        OpenClose openDay2 = new OpenClose();
        openDay2.setDay(2);
        openDay2.setTime("0700");

        OpenClose closeDay2 = new OpenClose();
        closeDay2.setDay(2);
        closeDay2.setTime("1900");

        periodDay2.setOpen(openDay2);
        periodDay2.setClose(closeDay2);

        periods.add(periodDay2);

        // Planning on wednesday
        Period periodDay3 = new Period();

        OpenClose openDay3 = new OpenClose();
        openDay3.setDay(3);
        openDay3.setTime("0900");

        OpenClose closeDay3 = new OpenClose();
        closeDay3.setDay(3);
        closeDay3.setTime("2000");

        periodDay3.setOpen(openDay3);
        periodDay3.setClose(closeDay3);

        periods.add(periodDay3);

        // Result under test
        actual = this.planningDeserializer.convertPeriods(periods);

    }


    @Test
    public void mapOpeningTimesForMonday() {

        assert(actual.get(1).get("open").getHour() == 8);
        assert(actual.get(1).get("open").getMinute() == 0);
        assert(actual.get(1).get("close").getHour() == 18);
        assert(actual.get(1).get("close").getMinute() == 30);

    }

    @Test
    public void mapOpeningTimesForTuesday() {

        assert(actual.get(2).get("open").getHour() == 7);
        assert(actual.get(2).get("open").getMinute() == 0);
        assert(actual.get(2).get("close").getHour() == 19);
        assert(actual.get(2).get("close").getMinute() == 0);

    }

    @Test
    public void mapOpeningTimesForWednesday() {

        assert(actual.get(3).get("open").getHour() == 9);
        assert(actual.get(3).get("open").getMinute() == 0);
        assert(actual.get(3).get("close").getHour() == 20);
        assert(actual.get(3).get("close").getMinute() == 0);

    }

    @Test
    public void shouldReturnNullForThurdayOff() {

        assertNull(actual.get(4));

    }


    @Test
    public void shouldReturnNullWhenPeriodsIsEmpty() {
        List<Period> periods = new ArrayList<>();
        PlanningDeserializer planningDeserializer = new PlanningDeserializer();
        // Under test
        Map<Integer, Map<String, LocalTime>> actual = planningDeserializer.convertPeriods(periods);
        assertNull(actual);

    }
}



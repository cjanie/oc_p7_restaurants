package com.android.go4lunch.apis.apiGoogleMaps.factories;

import com.android.go4lunch.apis.apiGoogleMaps.deserializers.place.Period;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanningFactory {

    public Map<Integer, Map<String, LocalTime>> convertPeriods(List<Period> periods) {

        if(periods == null || periods.isEmpty())
            return null;

        Map<Integer, Map<String, LocalTime>> planning = new HashMap<>();

        for(Period period: periods) {
            if(period.getOpen().getDay() > 0 && period.getOpen().getDay() < 8
                    && period.getOpen().getTime() != null
                    && period.getClose().getTime() != null
            ) {

                Map<String, LocalTime> times = new HashMap<>();
                times.put("open", LocalTime.of(
                        Integer.parseInt(period.getOpen().getTime().substring(0, 2)),
                        Integer.parseInt(period.getOpen().getTime().substring(2, 4))
                ));
                times.put("close", LocalTime.of(
                        Integer.parseInt(period.getClose().getTime().substring(0, 2)),
                        Integer.parseInt(period.getClose().getTime().substring(2, 4))
                ));
                planning.put(period.getOpen().getDay(), times);
            }
        }

        return planning;
    }



}

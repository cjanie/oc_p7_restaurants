package com.android.go4lunch.read.businesslogic.usecases.utils;

import com.android.go4lunch.read.businesslogic.usecases.enums.DistanceUnit;

import java.util.HashMap;
import java.util.Map;

public class DistanceUnitController {

    private DistanceUnit unity;

    public DistanceUnitController(DistanceUnit unity) {
        this.unity = unity;
    }

    public Map<DistanceUnit, Long> getDistanceInfo(Long distance) {
        Map<DistanceUnit, Long> map = new HashMap<>();
        if(this.unity.equals(DistanceUnit.UNITY)) {
            map.put(this.unity, distance);
        } else if(this.unity.equals(DistanceUnit.METER)) {
            // TODO
        }
        return map;
    }
}

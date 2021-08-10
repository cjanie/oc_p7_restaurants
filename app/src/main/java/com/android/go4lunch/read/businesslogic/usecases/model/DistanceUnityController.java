package com.android.go4lunch.read.businesslogic.usecases.model;

import java.util.HashMap;
import java.util.Map;

public class DistanceUnityController {

    private DistanceUnity unity;

    public DistanceUnityController(DistanceUnity unity) {
        this.unity = unity;
    }

    public Map<DistanceUnity, Long> getDistanceInfo(Long distance) {
        Map<DistanceUnity, Long> map = new HashMap<>();
        if(this.unity.equals(DistanceUnity.UNITY)) {
            map.put(this.unity, distance);
        } else if(this.unity.equals(DistanceUnity.METER)) {
            // TODO
        }
        return map;
    }
}

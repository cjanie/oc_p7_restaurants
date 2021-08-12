package com.android.go4lunch.read.adapter;

import com.android.go4lunch.read.businesslogic.gateways.TimeProvider;

import java.time.LocalTime;

public class RealTimeProvider implements TimeProvider {
    @Override
    public LocalTime now() {
        return LocalTime.now();
    }
}

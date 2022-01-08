package com.android.go4lunch.providers;

import java.time.LocalTime;

public class RealTimeProvider implements TimeProvider {
    @Override
    public LocalTime now() {
        return LocalTime.now();
    }
}

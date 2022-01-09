package com.android.go4lunch.deterministic_providers;

import com.android.go4lunch.providers.TimeProvider;

import java.time.LocalTime;

public class DeterministicTimeProvider implements TimeProvider {

    private LocalTime dateTimeOfNow;

    public DeterministicTimeProvider(LocalTime dateTimeOfNow) {
        this.dateTimeOfNow = dateTimeOfNow;
    }

    @Override
    public LocalTime now() {
        return this.dateTimeOfNow;
    }

    public void setDateTimeOfNow(LocalTime dateTimeOfNow) {
        this.dateTimeOfNow = dateTimeOfNow;
    }
}

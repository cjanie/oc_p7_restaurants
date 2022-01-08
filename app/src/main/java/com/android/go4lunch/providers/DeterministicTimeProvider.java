package com.android.go4lunch.providers;

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

package com.android.go4lunch.providers;

public class DeterministicDateProvider implements DateProvider {

    private int today;

    public DeterministicDateProvider(int today) {
        this.today = today;
    }

    @Override
    public int today() {
        return today;
    }
}

package com.android.go4lunch.deterministic_providers;

import com.android.go4lunch.providers.DateProvider;

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

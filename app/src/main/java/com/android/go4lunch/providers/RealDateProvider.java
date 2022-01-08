package com.android.go4lunch.providers;

import java.time.LocalDate;

public class RealDateProvider implements DateProvider {

    @Override
    public int today() {
        LocalDate localDate = LocalDate.now();
        return localDate.getDayOfWeek().getValue();
    }
}

package com.android.go4lunch.read.businesslogic.gateways;

import java.time.LocalTime;

public interface TimeProvider {

    LocalTime now();
}

package com.android.go4lunch.write.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;

public interface HistoricOfSelectionsCommand {
    void increment(Restaurant restaurant);
}

package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.write.businesslogic.gateways.HistoricOfSelectionsCommand;

public class UpdateHistoric {

    private HistoricOfSelectionsCommand historicOfSelectionsCommand;
    private Restaurant restaurant;

    public UpdateHistoric(HistoricOfSelectionsCommand historicOfSelectionsCommand, Restaurant restaurant) {
        this.historicOfSelectionsCommand = historicOfSelectionsCommand;
        this.restaurant = restaurant;
    }

    public void handle() {
        this.historicOfSelectionsCommand.increment(restaurant);
    }
}

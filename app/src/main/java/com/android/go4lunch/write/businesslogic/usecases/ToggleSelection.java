package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.RetrieveSession;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;

import java.util.List;

public class ToggleSelection {

    private SelectionCommand selectionCommand;

    private RetrieveSession retrieveSession;

    private Restaurant restaurant;

    public ToggleSelection(SelectionCommand selectionCommand, RetrieveSession retrieveSession, Restaurant restaurant) {
        this.selectionCommand = selectionCommand;
        this.retrieveSession = retrieveSession;
        this.restaurant = restaurant;
    }

    public void toggle() {
        Selection selection = new Selection(restaurant, this.retrieveSession.handle());
        this.selectionCommand.toggle(selection);
    }

}

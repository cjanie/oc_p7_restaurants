package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.RetrieveSession;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;

import java.util.List;

public class ToggleSelection {

    private SelectionCommand selectionCommand;

    private RetrieveSession retrieveSession;

    public ToggleSelection(SelectionCommand selectionCommand, RetrieveSession retrieveSession) {
        this.selectionCommand = selectionCommand;
        this.retrieveSession = retrieveSession;
    }

    public void toggle(Restaurant restaurant) {
        Selection selection = new Selection(restaurant, this.retrieveSession.handle());
        this.selectionCommand.toggle(selection);
    }

}

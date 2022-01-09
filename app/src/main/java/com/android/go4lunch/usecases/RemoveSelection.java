package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionCommand;
import com.android.go4lunch.models.Selection;


public class RemoveSelection {

    private SelectionCommand selectionCommand;

    public RemoveSelection(SelectionCommand selectionCommand) {
        this.selectionCommand = selectionCommand;
    }

    public void remove(Selection selection) {
        this.selectionCommand.remove(selection);
    }
}

package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;

public class ToggleSelection {

    private SelectionCommand selectionCommand;

    private Selection selection;

    public ToggleSelection(SelectionCommand selectionCommand, Selection selection) {
        this.selectionCommand = selectionCommand;
        this.selection = selection;
    }

    public void toggle() {
        this.selectionCommand.toggle(this.selection);
    }
}

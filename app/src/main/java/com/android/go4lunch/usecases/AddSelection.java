package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.gateways.SelectionCommand;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

public class AddSelection {

    private SelectionCommand selectionCommand;

    public AddSelection(SelectionCommand selectionCommand) {
        this.selectionCommand = selectionCommand;
    }

    public void add(Selection selection) {
        this.selectionCommand.add(selection);
    }
}

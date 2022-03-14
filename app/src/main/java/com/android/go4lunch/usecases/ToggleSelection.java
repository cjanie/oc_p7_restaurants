package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

public class ToggleSelection {

    private SelectionGateway selectionGateway;

    public ToggleSelection(SelectionGateway selectionGateway) {
        this.selectionGateway = selectionGateway;
    }

    public void handle(Selection selection) {
        if (this.selectionGateway.getSelection() == null) {
            this.selectionGateway.select(selection);
        } else {
            this.selectionGateway.unSelect();
        }
    }
}

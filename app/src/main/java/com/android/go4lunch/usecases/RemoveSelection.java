package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Selection;


public class RemoveSelection {

    private SelectionGateway selectionGateway;

    public RemoveSelection(SelectionGateway selectionGateway) {
        this.selectionGateway = selectionGateway;
    }

    public void remove(Selection selection) {
        this.selectionGateway.remove(selection);
    }
}

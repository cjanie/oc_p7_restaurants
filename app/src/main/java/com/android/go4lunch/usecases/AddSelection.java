package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Selection;

public class AddSelection {

    private SelectionGateway selectionGateway;

    public AddSelection(SelectionGateway selectionGateway) {
        this.selectionGateway = selectionGateway;
    }

    public void add(Selection selection) {
        this.selectionGateway.add(selection);
    }
}

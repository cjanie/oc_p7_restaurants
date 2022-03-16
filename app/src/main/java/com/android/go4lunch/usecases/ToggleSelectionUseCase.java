package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

public class ToggleSelectionUseCase {

    private SelectionGateway selectionGateway;

    private VisitorsGateway visitorsGateway;

    public ToggleSelectionUseCase(SelectionGateway selectionGateway, VisitorsGateway visitorsGateway) {
        this.selectionGateway = selectionGateway;
        this.visitorsGateway = visitorsGateway;
    }

    public void handle(Selection selection) {
        if (this.selectionGateway.getSelection() == null) {
            this.selectionGateway.select(selection);
            this.visitorsGateway.addSelection(selection);
        } else {
            this.selectionGateway.unSelect();
            this.visitorsGateway.removeSelection(selection.getWorkmateId());




        }
    }
}

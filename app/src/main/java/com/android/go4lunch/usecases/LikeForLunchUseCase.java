package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;

import java.util.ArrayList;
import java.util.List;

public class LikeForLunchUseCase {

    private final SelectionGateway selectionGateway;

    private final VisitorsGateway visitorsGateway;



    public LikeForLunchUseCase(
            SelectionGateway selectionGateway,
            VisitorsGateway visitorsGateway
    ) {
        this.selectionGateway = selectionGateway;
        this.visitorsGateway = visitorsGateway;
    }

    public void handle(String restaurantId, String restaurantName, String workmateId) {
        Selection selection = new Selection(
                restaurantId,
                workmateId
        );

        if (this.selectionGateway.getSelection() == null) {
            this.selectionGateway.select(selection);
            this.visitorsGateway.addSelection(selection);

        } else {
            this.selectionGateway.unSelect();
            this.visitorsGateway.removeSelection(selection.getWorkmateId());
        }

    }
}

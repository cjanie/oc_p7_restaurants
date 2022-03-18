package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;

import java.util.ArrayList;
import java.util.List;

public class ToggleSelectionUseCase {

    private final SelectionGateway selectionGateway;

    private final VisitorsGateway visitorsGateway;

    private final GetSessionUseCase getSessionUseCase;

    public ToggleSelectionUseCase(
            SelectionGateway selectionGateway,
            VisitorsGateway visitorsGateway,
            GetSessionUseCase getSessionUseCase
    ) {
        this.selectionGateway = selectionGateway;
        this.visitorsGateway = visitorsGateway;
        this.getSessionUseCase = getSessionUseCase;
    }

    public void handle(String restaurantId, String restaurantName) throws NoWorkmateForSessionException {
        if(restaurantId != null && restaurantName !=null) {
            List<Workmate> resultsSession = new ArrayList<>();
            this.getSessionUseCase.getWorkmate().subscribe(resultsSession::add);
            if(!resultsSession.isEmpty()) {
                Selection selection = new Selection(
                        restaurantId,
                        restaurantName,
                        resultsSession.get(0).getId(),
                        resultsSession.get(0).getId()
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

    }
}

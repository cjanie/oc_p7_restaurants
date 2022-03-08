package com.android.go4lunch.usecases;


import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.HistoricOfSelectionsGateway;
import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;

import java.util.List;

import io.reactivex.Observable;

public class ToggleSelection {

    private final SelectionGateway selectionGateway;

    private final GetSession getSession;

    private final HistoricOfSelectionsGateway historicOfSelectionsGateway;

    public ToggleSelection(SelectionGateway selectionGateway,
                           GetSession getSession,
                           HistoricOfSelectionsGateway historicOfSelectionsGateway) {
        this.selectionGateway = selectionGateway;
        this.getSession = getSession;
        this.historicOfSelectionsGateway = historicOfSelectionsGateway;
    }

    public Observable<List<Selection>> toggle(Restaurant restaurant) throws NoWorkmateForSessionException {
        return this.getSession.getWorkmate().flatMap(workmate -> {
            Selection selection = new Selection(restaurant, workmate);
            return handle(selection);
        });
    }

    private Observable<List<Selection>> handle(Selection selection) {

        return this.selectionGateway.getSelections().map(selections -> {
            if(selections.isEmpty()) {
                this.add(selection);
            } else {
                Selection foundSame = null;
                Selection foundAnotherButSameWorkmate = null;

                for(int i=0; i<selections.size(); i++) {
                    Selection s = selections.get(i);
                    if(s.getWorkmate().equals(selection.getWorkmate())) {
                        if(s.getRestaurant().equals(selection.getRestaurant())) {
                            foundSame = s;
                            break;
                        } else {
                            foundAnotherButSameWorkmate = s;
                            break;
                        }
                    }
                }

                if(foundSame == null) {
                    this.add(selection);
                }

                else this.remove(foundSame);

                if(foundAnotherButSameWorkmate != null)
                    this.remove(foundAnotherButSameWorkmate);

            }
            return selections;
        });
    }

    private void add(Selection selection) {
        this.selectionGateway.add(selection);
        new UpdateHistoric(this.historicOfSelectionsGateway, selection.getRestaurant()).handle();
    }

    private void remove(Selection selection) {
        this.selectionGateway.remove(selection);
    }

}

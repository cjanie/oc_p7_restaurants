package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.gateways.HistoricOfSelectionsRepository;
import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.SelectionCommand;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ToggleSelection {

    private final SelectionCommand selectionCommand;

    private final GetSession getSession;

    private final HistoricOfSelectionsRepository historicRepository;

    public ToggleSelection(SelectionCommand selectionCommand,
                           GetSession getSession,
                           HistoricOfSelectionsRepository historicRepository) {
        this.selectionCommand = selectionCommand;
        this.getSession = getSession;
        this.historicRepository = historicRepository;
    }

    public Observable<List<Selection>> toggle(Restaurant restaurant) throws NoWorkmateForSessionException {
        return this.getSession.getWorkmate().flatMap(workmate -> {
            Selection selection = new Selection(restaurant, workmate);
            return handle(selection);
        });
    }

    private Observable<List<Selection>> handle(Selection selection) {

        return this.selectionCommand.getSelections().map(selections -> {
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
        this.selectionCommand.add(selection);
        new UpdateHistoric(this.historicRepository, selection.getRestaurant()).handle();
    }

    private void remove(Selection selection) {
        this.selectionCommand.remove(selection);
    }

}

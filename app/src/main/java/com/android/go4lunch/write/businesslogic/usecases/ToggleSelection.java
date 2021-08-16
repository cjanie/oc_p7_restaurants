package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.HistoricOfSelectionsRepository;
import com.android.go4lunch.SelectionRepository;
import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.RetrieveSession;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.HistoricOfSelectionsCommand;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;

import java.util.List;

public class ToggleSelection {

    private SelectionRepository selectionRepository;

    private RetrieveSession retrieveSession;

    private HistoricOfSelectionsRepository historicRepository;

    public ToggleSelection(SelectionRepository selectionRepository,
                           RetrieveSession retrieveSession,
                           HistoricOfSelectionsRepository historicRepository) {
        this.selectionRepository = selectionRepository;
        this.retrieveSession = retrieveSession;
        this.historicRepository = historicRepository;
    }

    public void toggle(Restaurant restaurant) {
        Selection selection = new Selection(restaurant, this.retrieveSession.handle());
        this.handle(selection);
    }

    private void handle(Selection selection) {

        if(this.selectionRepository.findAll().isEmpty())
            this.add(selection);

        else {
            Selection foundSame = null;
            Selection foundAnotherButSameWorkmate = null;

            for(int i=0; i<this.selectionRepository.findAll().size(); i++) {
                Selection s = this.selectionRepository.findAll().get(i);
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
    }

    private void add(Selection selection) {
        this.selectionRepository.add(selection);
        new UpdateHistoric(this.historicRepository, selection.getRestaurant()).handle();
    }

    private void remove(Selection selection) {
        this.selectionRepository.remove(selection);
    }


}

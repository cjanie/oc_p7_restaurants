package com.android.go4lunch.write.businesslogic.usecases;

import com.android.go4lunch.HistoricOfSelectionsRepository;
import com.android.go4lunch.SelectionRepository;
import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.GetSession;

public class ToggleSelection {

    private SelectionRepository selectionRepository;

    private GetSession getSession;

    private HistoricOfSelectionsRepository historicRepository;

    public ToggleSelection(SelectionRepository selectionRepository,
                           GetSession getSession,
                           HistoricOfSelectionsRepository historicRepository) {
        this.selectionRepository = selectionRepository;
        this.getSession = getSession;
        this.historicRepository = historicRepository;
    }

    public void toggle(Restaurant restaurant) throws NoWorkmateForSessionException {
        //Selection selection = new Selection(restaurant, this.getSession.getWorkmate());
        //this.handle(selection);
    }

    private void handle(Selection selection) {
    /*
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

     */
    }

    private void add(Selection selection) {
        this.selectionRepository.add(selection);
        new UpdateHistoric(this.historicRepository, selection.getRestaurant()).handle();
    }

    private void remove(Selection selection) {
        this.selectionRepository.remove(selection);
    }


}

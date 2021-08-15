package com.android.go4lunch;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.HistoricOfSelectionsCommand;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;
import com.android.go4lunch.write.businesslogic.usecases.UpdateHistoric;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCurrentSelectionsRepository implements SelectionQuery, SelectionCommand {

    private List<Selection> selections;

    private HistoricOfSelectionsCommand historicCommand;

    public InMemoryCurrentSelectionsRepository(HistoricOfSelectionsCommand historicCommand) {
        this.selections = new ArrayList<>();
        this.historicCommand = historicCommand;
    }

    public void setSelections(List<Selection> selections) {
        this.selections = selections;
    }

    @Override
    public List<Selection> findAll() {
        return this.selections;
    }


    @Override
    public void toggle(Selection selection) {

        if(this.selections.isEmpty())
            this.add(selection);

        else {
            Selection foundSame = null;
            Selection foundAnotherButSameWorkmate = null;

            for(int i=0; i<this.selections.size(); i++) {
                Selection s = this.selections.get(i);
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
        this.selections.add(selection);
        new UpdateHistoric(this.historicCommand, selection.getRestaurant()).handle();
    }

    private void remove(Selection selection) {
        this.selections.remove(selection);
    }


}

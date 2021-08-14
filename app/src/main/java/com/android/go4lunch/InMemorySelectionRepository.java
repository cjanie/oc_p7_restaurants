package com.android.go4lunch;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;
import com.android.go4lunch.write.businesslogic.gateways.HistoricOfSelectionsCommand;
import com.android.go4lunch.write.businesslogic.gateways.SelectionCommand;
import com.android.go4lunch.write.businesslogic.usecases.IncrementSelectionsCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemorySelectionRepository implements SelectionQuery, SelectionCommand {

    private List<Selection> selections;

    private HistoricOfSelectionsCommand historicCommand;

    public InMemorySelectionRepository(HistoricOfSelectionsCommand historicCommand) {
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
            this.selections.add(selection);
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
                this.selections.add(selection);
                new IncrementSelectionsCount(this.historicCommand, selection.getRestaurant()).handle();
            }

            else this.selections.remove(foundSame);

            if(foundAnotherButSameWorkmate != null)
                this.selections.remove(foundAnotherButSameWorkmate);

        }
    }


}

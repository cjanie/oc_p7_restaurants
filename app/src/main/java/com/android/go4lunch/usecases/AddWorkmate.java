package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.WorkmateCommand;
import com.android.go4lunch.models.Workmate;

import java.util.List;

import io.reactivex.Observable;

public class AddWorkmate {

    private WorkmateCommand workmateCommand;

    public AddWorkmate(WorkmateCommand workmateCommand) {
        this.workmateCommand = workmateCommand;
    }

    private Observable<List<Workmate>> add(Workmate workmate) {
        return this.workmateCommand.getWorkmates().map(workmates -> {
            if(workmates.isEmpty()) {
                workmates.add(workmate);
            } else {
                for(int i=0; i<workmates.size(); i++) {
                    if(workmate.getEmail().equals(workmates.get(i).getEmail())) { // has account
                        if(!workmate.getName().equals(workmates.get(i).getName())) { // update case
                            workmates.get(i).setName(workmate.getName());
                        }
                    } else { // has no account
                        workmates.add(workmate);
                    }
                }
            }
            return workmates;
        });
    }

    public void save(Workmate workmate) {
        this.workmateCommand.setWorkmates(this.add(workmate));
    }
}

package com.android.go4lunch.usecases;

import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmatesUseCase {

    private WorkmateGateway workmateGateway;

    private SessionGateway sessionGateway;

    public GetWorkmatesUseCase(
            WorkmateGateway workmateGateway,
            SessionGateway sessionGateway
    ) {
        this.workmateGateway = workmateGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<List<Workmate>> handle() {
        return this.workmateGateway.getWorkmates().flatMap(workmates ->
            this.filterWorkmatesRemovingSession(workmates)
        );
    }

    private Observable<List<Workmate>> filterWorkmatesRemovingSession(List<Workmate> workmates) {
        return this.sessionGateway.getSession().map(session -> {
            List<Workmate> filteredList = new ArrayList<>();
            if(!workmates.isEmpty()) {
                for(Workmate workmate: workmates) {
                    if(session != null) {
                        if(!workmate.getId().equals(session.getId())) {
                            filteredList.add(workmate);
                        }
                    } else {
                        filteredList = workmates;
                    }
                }
            }
            return filteredList;
        });
    }
}

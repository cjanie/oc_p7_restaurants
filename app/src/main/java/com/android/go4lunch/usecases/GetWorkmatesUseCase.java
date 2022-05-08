package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.usecases.models.WorkmateModel;

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
        return this.workmateGateway.getWorkmates().map(workmates ->
            this.getFilteredListOfWorkmatesRemovingTheWorkmateOfTheCurrentSession(workmates, this.getSession())
        );
    }

    private List<Workmate> getFilteredListOfWorkmatesRemovingTheWorkmateOfTheCurrentSession(List<Workmate> workmates, Workmate workmateSession) {
        List<Workmate> filteredList = new ArrayList<>();
        if(workmateSession == null)
            filteredList = workmates;
        if(workmateSession!= null && workmates != null && !workmates.isEmpty()) {
            for(int i=0; i<workmates.size(); i++) {
                if(!workmates.get(i).getId().equals(workmateSession.getId())) {
                    filteredList.add(workmates.get(i));
                }
            }
        }
        return filteredList;
    }

    private Workmate getSession() {
        List<Workmate> workmateSessionResults = new ArrayList<>();
        if(sessionGateway.getSession() != null)
            this.sessionGateway.getSession().subscribe(workmateSessionResults::add);
        if(!workmateSessionResults.isEmpty())
            return workmateSessionResults.get(0);
        return null;
    }
}

package com.android.go4lunch.usecases;

import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmateByIdUseCase {

    private WorkmateGateway workmateGateway;

    public GetWorkmateByIdUseCase(WorkmateGateway workmateGateway) {
        this.workmateGateway = workmateGateway;
    }

    public Observable<Workmate> handle(String workmateId) throws NotFoundException {
        List<Workmate> workmatesResults = new ArrayList<>();
        this.workmateGateway.getWorkmates().subscribe(workmatesResults::addAll);
        Workmate workmate = null;
        if(!workmatesResults.isEmpty()) {
            for(Workmate w: workmatesResults) {
                if(w.getId() == workmateId) {
                    workmate = w;
                    break;
                }
            }
        }

        if(workmate == null) {
            throw new NotFoundException();
        }

        return Observable.just(workmate);

    }

}

package com.android.go4lunch.in_memory_gateways;

import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemoryWorkmateGateway implements WorkmateGateway {

    private Observable<List<Workmate>> workmates;

    public InMemoryWorkmateGateway() {
        this.workmates = Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() {
        return this.workmates;
    }

    @Override
    public void saveWorkmate(Workmate workmate) {
        
    }

    public void setWorkmates(List<Workmate> workmates) {
        this.workmates = Observable.just(workmates);
    }


}

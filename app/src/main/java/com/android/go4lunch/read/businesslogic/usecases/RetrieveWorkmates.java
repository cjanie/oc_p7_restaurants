package com.android.go4lunch.read.businesslogic.usecases;

import com.android.go4lunch.read.businesslogic.gateways.WorkmateQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import java.util.ArrayList;
import java.util.List;

public class RetrieveWorkmates {

    private WorkmateQuery workmateQuery;

    public RetrieveWorkmates(WorkmateQuery workmateQuery) {
        this.workmateQuery = workmateQuery;
    }

    public List<WorkmateVO> handle() {
        List<WorkmateVO> workmates = new ArrayList<>();
        for(Workmate w: this.findAll()) {
            WorkmateVO workmateVO = new WorkmateVO(w);
            workmates.add(workmateVO);
        }
        return workmates;
    }

    private List<Workmate> findAll() {
        return this.workmateQuery.findAll();
    }
}

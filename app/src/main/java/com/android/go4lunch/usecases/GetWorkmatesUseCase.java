package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmatesUseCase {

    private WorkmateGateway workmateGateway;

    public GetWorkmatesUseCase(WorkmateGateway workmateGateway) {
        this.workmateGateway = workmateGateway;
    }

    public Observable<List<WorkmateVO>> list() {
        return this.workmateGateway.getWorkmates().map(workmates -> {
            List<WorkmateVO> workmateVOs = new ArrayList<>();
            if(!workmates.isEmpty()) {
                for(Workmate workmate: workmates) {
                    WorkmateVO workmateVO = new WorkmateVO(workmate);
                    workmateVOs.add(workmateVO);
                }
            }
            return workmateVOs;
        });
    }
}

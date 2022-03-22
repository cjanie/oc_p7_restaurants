package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class SelectionInfoDecoratorForWorkMate {

    private List<Selection> selections;

    public SelectionInfoDecoratorForWorkMate(List<Selection> selections) {
        this.selections = selections;
    }

    public WorkmateVO decor(Workmate workmate) {
        WorkmateVO workmateVO = new WorkmateVO(workmate);;
        if(!this.selections.isEmpty()) {
            for(Selection s: this.selections) {
                if(s.getWorkmateId().equals(workmate.getId())) {
                    workmateVO.setSelection(new Restaurant("resto1", "address"));
                }
            }
        }

        return workmateVO;
    }

}

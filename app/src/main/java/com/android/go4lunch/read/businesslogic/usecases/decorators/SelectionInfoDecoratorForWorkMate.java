package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.Decorator;
import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.WorkmateVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

import java.util.List;

public class SelectionInfoDecoratorForWorkMate implements Decorator<WorkmateVO> {

    private SelectionQuery selectionQuery;

    public SelectionInfoDecoratorForWorkMate(SelectionQuery selectionQuery) {
        this.selectionQuery = selectionQuery;
    }

    @Override
    public WorkmateVO decor(WorkmateVO workmate) {
        workmate.setSelection(this.getSelection(workmate));
        return workmate;
    }

    private Restaurant getSelection(WorkmateVO workmate) {
        Restaurant selected = null;

        for(Selection s: this.selectionQuery.findAll()) {
            if(s.getWorkmate().equals(workmate.getWorkmate())) {
                selected = s.getRestaurant();
                break;
            }
        }
        return selected;
    }

}

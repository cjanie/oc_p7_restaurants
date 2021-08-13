package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.WorkmateVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Restaurant;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

import java.util.List;

public class SelectionInfoDecoratorForWorkMate extends SelectionInfoDecorator<WorkmateVO> {

    private WorkmateVO workmateVO;

    public SelectionInfoDecoratorForWorkMate(SelectionQuery selectionQuery, WorkmateVO workmateVO) {
        this.selectionQuery = selectionQuery;
        this.workmateVO = workmateVO;
    }

    @Override
    public WorkmateVO decor() {
        // TODO decor
        this.workmateVO.setSelection(this.getSelection());
        return this.workmateVO;
    }

    private Restaurant getSelection() {
        Restaurant selected = null;

        for(Selection s: this.selectionQuery.findAll()) {
            if(s.getWorkmate().equals(this.workmateVO.getWorkmate())) {
                selected = s.getRestaurant();
                break;
            }
        }
        return selected;
    }

}

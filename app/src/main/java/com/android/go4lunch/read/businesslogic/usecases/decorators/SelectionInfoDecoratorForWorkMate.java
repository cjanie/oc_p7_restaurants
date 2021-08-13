package com.android.go4lunch.read.businesslogic.usecases.decorators;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.WorkmateVO;

public class SelectionInfoDecoratorForWorkMate extends SelectionInfoDecorator<WorkmateVO> {

    private WorkmateVO workmateVO;

    public SelectionInfoDecoratorForWorkMate(SelectionQuery selectionQuery, WorkmateVO workmateVO) {
        this.selectionQuery = selectionQuery;
        this.workmateVO = workmateVO;
    }

    @Override
    public WorkmateVO decor() {
        // TODO decor
        //this.workmateVO.setSelectionInfo()
        return this.workmateVO;
    }

}

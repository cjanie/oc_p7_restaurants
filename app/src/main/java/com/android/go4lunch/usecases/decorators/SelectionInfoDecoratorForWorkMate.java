package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.gateways.SelectionQuery;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;

import io.reactivex.Observable;

public class SelectionInfoDecoratorForWorkMate {

    private SelectionQuery selectionQuery;

    public SelectionInfoDecoratorForWorkMate(SelectionQuery selectionQuery) {
        this.selectionQuery = selectionQuery;
    }

    public Observable<WorkmateVO> decor(WorkmateVO workmate) {
        return this.getSelection(workmate).map(restaurant -> {
            workmate.setSelection(restaurant);
            return workmate;
        });
    }

    private Observable<Restaurant> getSelection(WorkmateVO workmate) {
        return this.selectionQuery.getSelections().map(selections -> {
            Restaurant selected = null;
            if(!selections.isEmpty()) {
                for(Selection s: selections) {
                    if(s.getWorkmate().equals(workmate.getWorkmate())) {
                        selected = s.getRestaurant();
                        break;
                    }
                }
            }
            return selected;
        });
    }

}
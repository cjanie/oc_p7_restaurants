package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.usecases.models_vo.WorkmateVO;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;

import io.reactivex.Observable;

public class SelectionInfoDecoratorForWorkMate {

    private SelectionGateway selectionGateway;

    public SelectionInfoDecoratorForWorkMate(SelectionGateway selectionGateway) {
        this.selectionGateway = selectionGateway;
    }

    public Observable<WorkmateVO> decor(WorkmateVO workmate) {
        return this.getSelection(workmate).map(restaurant -> {
            workmate.setSelection(restaurant);
            return workmate;
        });
    }

    private Observable<Restaurant> getSelection(WorkmateVO workmate) {
        return this.selectionGateway.getSelection().map(selection -> {
            Restaurant selected = selection.getRestaurant();
            return selected;
        });
    }

}

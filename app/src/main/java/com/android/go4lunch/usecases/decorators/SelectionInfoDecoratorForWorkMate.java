package com.android.go4lunch.usecases.decorators;

import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.usecases.models.WorkmateModel;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.models.Selection;

import java.util.List;

public class SelectionInfoDecoratorForWorkMate {

    private List<Selection> selections;

    public SelectionInfoDecoratorForWorkMate(List<Selection> selections) {
        this.selections = selections;
    }

    public WorkmateModel decor(Workmate workmate) {
        WorkmateModel workmateModel = new WorkmateModel(workmate);;
        if(!this.selections.isEmpty()) {
            for(Selection s: this.selections) {
                if(s.getWorkmateId().equals(workmate.getId())) {
                    workmateModel.setSelection(new Restaurant("resto1", "address"));
                }
            }
        }

        return workmateModel;
    }

}

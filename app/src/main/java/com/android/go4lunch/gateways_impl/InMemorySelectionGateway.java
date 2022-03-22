package com.android.go4lunch.gateways_impl;


import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class InMemorySelectionGateway implements SelectionGateway {

    private Observable<Selection> selection;

    public void setSelection(Selection selection) {
        if(selection != null) {
            this.selection = Observable.just(selection);
        }
    }

    @Override
    public Observable<Selection> getSelection() {
        return this.selection;
    }


    @Override
    public void select(Selection selection) {
        if(selection != null) {
            this.selection = Observable.just(selection);
        }

    }

    @Override
    public void unSelect() {
        this.selection = null;
    }


}

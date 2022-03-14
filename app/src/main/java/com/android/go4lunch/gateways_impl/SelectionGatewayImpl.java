package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Selection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class SelectionGatewayImpl implements SelectionGateway {

    Observable<Selection> selection;

    @Override
    public Observable<Selection> getSelection() {
        return this.selection;
    }

    @Override
    public void select(@NonNull Selection selection) {
        this.selection = Observable.just(selection);
    }

    @Override
    public void unSelect() {
        this.selection = null;
    }
}

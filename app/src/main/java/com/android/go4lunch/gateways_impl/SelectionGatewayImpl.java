package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.SelectionGateway;
import com.android.go4lunch.models.Selection;

import java.util.List;

import io.reactivex.Observable;

public class SelectionGatewayImpl implements SelectionGateway {
    @Override
    public Observable<List<Selection>> getSelections() {
        return null;
    }

    @Override
    public void add(Selection selection) {

    }

    @Override
    public void remove(Selection selection) {

    }
}

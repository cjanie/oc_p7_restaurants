package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Selection;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;

public interface SelectionGateway {

    Observable<Selection> getSelection();

    void select(Selection selection);

    void unSelect();

}
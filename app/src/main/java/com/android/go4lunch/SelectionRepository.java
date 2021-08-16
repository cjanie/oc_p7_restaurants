package com.android.go4lunch;

import com.android.go4lunch.read.businesslogic.gateways.SelectionQuery;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

import java.util.List;

public interface SelectionRepository extends SelectionQuery {



    void add(Selection selection);

    void remove(Selection selection);
}

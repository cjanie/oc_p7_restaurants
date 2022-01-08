package com.android.go4lunch;

import com.android.go4lunch.gateways.SelectionQuery;
import com.android.go4lunch.models.Selection;

public interface SelectionRepository extends SelectionQuery {

    void add(Selection selection);

    void remove(Selection selection);
}

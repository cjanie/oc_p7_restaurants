package com.android.go4lunch.gateways;

import com.android.go4lunch.gateways.DistanceQuery;
import com.android.go4lunch.models.Selection;

public interface SelectionCommand extends SelectionQuery {

    void add(Selection selection);

    void remove(Selection selection);
}

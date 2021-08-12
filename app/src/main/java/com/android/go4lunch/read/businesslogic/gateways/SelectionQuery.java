package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

import java.util.List;
import java.util.Map;

public interface SelectionQuery {

    int getSelectionsCount();

    List<Selection> getSelections();
}

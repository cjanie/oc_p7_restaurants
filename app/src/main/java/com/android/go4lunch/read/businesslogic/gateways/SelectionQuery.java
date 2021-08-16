package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.SelectionRepository;
import com.android.go4lunch.read.businesslogic.usecases.model.Selection;

import java.util.List;

public interface SelectionQuery {
    List<Selection> findAll();
}

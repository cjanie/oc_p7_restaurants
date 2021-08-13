package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.model.Workmate;

import java.util.List;

public interface WorkmateQuery {

    public List<Workmate> findAll();
}

package com.android.go4lunch.businesslogic.gateways;

import com.android.go4lunch.businesslogic.entities.Workmate;

import java.util.List;

import io.reactivex.Observable;

public interface WorkmateGateway {

    Observable<List<Workmate>> getWorkmates();

    void saveWorkmate(Workmate workmate);

}

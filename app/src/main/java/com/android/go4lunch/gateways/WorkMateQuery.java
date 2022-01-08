package com.android.go4lunch.gateways;

import com.android.go4lunch.models.Workmate;

import java.util.List;

import io.reactivex.Observable;

public interface WorkMateQuery {

    Observable<List<Workmate>> getWorkmates();

}

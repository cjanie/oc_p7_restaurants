package com.android.go4lunch.repositories;

import com.android.go4lunch.gateways.WorkMateQuery;
import com.android.go4lunch.models.Workmate;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class WorkmateRepository implements WorkMateQuery {
    @Override
    public Observable<List<Workmate>> getWorkmates() {
        return Observable.just(Arrays.asList(new Workmate("Janie"), new Workmate("Cyril"), new Workmate("Toto")));
    }
}

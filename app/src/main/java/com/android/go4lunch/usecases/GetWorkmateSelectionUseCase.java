package com.android.go4lunch.usecases;

import android.util.Log;

import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class GetWorkmateSelectionUseCase {

    private final String TAG = "WORKMATE SELECTION UC";

    private VisitorGateway visitorGateway;

    private BehaviorSubject workmateSelectionSubject;

    public GetWorkmateSelectionUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
        this.workmateSelectionSubject = BehaviorSubject.create();
    }

    public Observable<Selection> handle(String workmateId) {

        this.visitorGateway.getSelections().subscribe(selections -> {

            Log.d(TAG, "-- handle -- selections size: " + selections.size());

            if(!selections.isEmpty()) {
                for(Selection s: selections) {
                    if(s.getWorkmateId() == workmateId) {
                        this.workmateSelectionSubject.onNext(s);
                        break;
                    }
                }
            }
        });

        return this.workmateSelectionSubject.hide();
    }


}

package com.android.go4lunch.businesslogic.usecases;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.models.WorkmateEntityModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmatesUseCase {

    private final String TAG = "GET WORKMATES USE CASE";

    private WorkmateGateway workmateGateway;

    private SessionGateway sessionGateway;

    private VisitorGateway visitorGateway;

    public GetWorkmatesUseCase(
            WorkmateGateway workmateGateway,
            SessionGateway sessionGateway,
            VisitorGateway visitorGateway
    ) {
        this.workmateGateway = workmateGateway;
        this.sessionGateway = sessionGateway;
        this.visitorGateway = visitorGateway;
    }

    public Observable<List<Workmate>> handle() {
        //return this.getWorkmatesExceptSession();
        return this.getWorkmatesExceptSessionWithWorkmateSelectedRestaurant();
    }

    private Observable<List<Workmate>> getWorkmatesExceptSessionWithWorkmateSelectedRestaurant() {
        return this.getWorkmatesExceptSession()
                .flatMap(workmates ->
                        Observable.fromIterable(workmates)
                                .flatMap(
                                        workmate -> {
                                            Log.d(this.TAG, "getWorkmatesExceptSessionWithWorkmateSelectedRestaurant" + workmate.getName() + " selection: " + workmate.getSelectedRestaurant());
                                            return this.createWorkmateValueObjectWithSelection(workmate);
                                        }

                                ).toList().toObservable()
                );
    }

    private Observable<List<Workmate>> getWorkmatesExceptSession() {
        return this.workmateGateway.getWorkmates().flatMap(workmates ->
                this.filterWorkmatesRemovingSession(workmates)
        );
    }

    private Observable<List<Workmate>> filterWorkmatesRemovingSession(List<Workmate> workmates) {
        return this.sessionGateway.getSession().map(session -> {

            if(session == null) {
                return workmates;
            } else {
                if(!workmates.isEmpty()) {
                    List<Workmate> filteredList = new ArrayList<>();
                    for(Workmate workmate: workmates) {
                        if(!workmate.getId().equals(session.getId())) {
                            filteredList.add(workmate);
                        }
                    }
                    return filteredList;
                }
                return workmates;
            }
        });
    }

    private Observable<Workmate> createWorkmateValueObjectWithSelection(Workmate workmate) {
        return this.visitorGateway.getSelections().map(
                selections -> {
                    Log.d(this.TAG, "createWorkmateValueObjectWithSelection " + "selections size: " + selections.size());
                    Workmate workmateCopy = workmate;
                    if(!selections.isEmpty()) {
                        for(Selection selection: selections) {
                            if(selection.getWorkmateId().equals(workmate.getId())) {
                                Restaurant selectedRestaurant = new Restaurant(selection.getRestaurantName());
                                selectedRestaurant.setId(selection.getRestaurantId());
                                workmateCopy = new WorkmateEntityModel().setWorkmateSelectedRestaurant(workmate, selectedRestaurant);
                                Log.d(this.TAG, "createWorkmateValueObjectWithSelection " + workmateCopy.getName() + " selection: " + workmateCopy.getSelectedRestaurant());
                                break;
                            }
                        }
                    }

                    return workmateCopy;


                }
        );
    }

}

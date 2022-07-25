package com.android.go4lunch.businesslogic.usecases.workmate;

import android.util.Log;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.models.RestaurantModel;
import com.android.go4lunch.businesslogic.models.WorkmateModel;
import com.android.go4lunch.businesslogic.valueobjects.WorkmateValueObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GetWorkmatesUseCase {

    private final String TAG = "GET WORKMATES USE CASE";

    private WorkmateGateway workmateGateway;

    private SessionGateway sessionGateway;

    private VisitorGateway visitorGateway;

    private final WorkmateModel workmateModel;

    private final RestaurantModel restaurantModel;

    public GetWorkmatesUseCase(
            WorkmateGateway workmateGateway,
            SessionGateway sessionGateway,
            VisitorGateway visitorGateway
    ) {
        this.workmateGateway = workmateGateway;
        this.sessionGateway = sessionGateway;
        this.visitorGateway = visitorGateway;

        this.workmateModel = new WorkmateModel();
        this.restaurantModel = new RestaurantModel();
    }

    public Observable<List<WorkmateValueObject>> handle() {
        return this.getFilteredWorkmatesAfterRemovingSessionWithTheirSelectedRestaurant()
                .doOnNext(workmateValueObjects -> {
                    Log.d(TAG, "--handle : " + Thread.currentThread().getName());
                });
    }

    private Observable<List<WorkmateValueObject>> getFilteredWorkmatesAfterRemovingSessionWithTheirSelectedRestaurant() {
        return this.getFilteredWorkmatesAtferRemovingSessionFormatedToWorkmateVOs()
                .flatMap(workmateVOs ->
                        this.updateWorkmatesWithTheirSelectedRestaurant(workmateVOs));
    }

    // getters for Gateways data
    private Observable<List<Workmate>> getWorkmates() {
        return this.workmateGateway.getWorkmates();
    }

    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }

    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }

    private Observable<List<Workmate>> getFilteredWorkmatesAfterRemovingSession() {
        return this.getWorkmates()
                .flatMap(workmates -> {
                    Log.d(TAG, "--getfilteredWorkmatesAfterRemovingSession : " + Thread.currentThread().getName());
                    return this.filterWorkmatesRemovingSession(workmates);
                });
    }

    private Observable<List<Workmate>> filterWorkmatesRemovingSession(List<Workmate> workmates) {
        return this.getSession()
                .map(session -> {
                    Log.d(TAG, "--filterWorkmatesRemovingSession : " + Thread.currentThread().getName());

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

    private Observable<List<WorkmateValueObject>> getFilteredWorkmatesAtferRemovingSessionFormatedToWorkmateVOs() {
        return this.getFilteredWorkmatesAfterRemovingSession()
                .map(filteredWorkmates -> {
                    List<WorkmateValueObject> workmateVOs = this.workmateModel.formatWorkmatesToWorkmateVOs(filteredWorkmates);
                    Log.d(TAG, "--getFilteredWorkmatesAfterRemovingSessionFormatedToWorkmateVOs : " + Thread.currentThread().getName());
                    return workmateVOs;
                });
    }

    private Observable<List<WorkmateValueObject>> updateWorkmatesWithTheirSelectedRestaurant(List<WorkmateValueObject> workmateVOs) {
        return this.getSelections().map(selections -> {
            Log.d(TAG, "--updateWorkmatesWithTheirSelectedRestaurant : " + Thread.currentThread().getName());

            if(!workmateVOs.isEmpty()) {
                for(WorkmateValueObject workmateVO: workmateVOs) {
                    Restaurant selection = this.restaurantModel
                            .findWorkmateSelection(
                                    workmateVO.getWorkmate().getId(),
                                    selections
                            );
                    if(selection != null) {
                        workmateVO.setSelection(selection);
                        Log.d(TAG, "--getWorkmatesWithSelectedRestaurant : " + Thread.currentThread().getName());
                    }
                }
            }
            return workmateVOs;
        });
    }
}

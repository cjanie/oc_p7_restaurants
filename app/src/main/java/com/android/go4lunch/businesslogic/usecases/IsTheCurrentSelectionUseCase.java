package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class IsTheCurrentSelectionUseCase {

    private VisitorGateway visitorGateway;

    private SessionGateway sessionGateway;


    public IsTheCurrentSelectionUseCase(VisitorGateway visitorGateway, SessionGateway sessionGateway) {
        this.visitorGateway = visitorGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> handle(String restaurantId) {
        return this.getSelections()
                .flatMap(selections -> this.findCurrentSelection(selections, restaurantId));

                        /*
                        Observable.fromIterable(selections)
                                .flatMap(selection -> this.findCurrentSelection(selections, restaurantId))
                */

    }

    private Observable<List<Selection>> getSelections() {
        return this.visitorGateway.getSelections();
    }

    private Observable<Boolean> findCurrentSelection(List<Selection> selections, String restaurantId) {
        return this.getSession().map(session -> {
            if(!selections.isEmpty()) {
                for(Selection selection: selections) {
                    if(selection.getWorkmateId().equals(session.getId()) && selection.getRestaurantId().equals(restaurantId)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }

}

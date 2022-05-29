package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.models.SelectionModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class GoForLunchUseCase {

    private final VisitorGateway visitorGateway;

    private final SessionGateway sessionGateway;

    public GoForLunchUseCase(
            VisitorGateway visitorGateway,
            SessionGateway sessionGateway) {
        this.visitorGateway = visitorGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> handle(String restaurantId, String restaurantName) {
        return this.toggle(restaurantId, restaurantName);
    }

    private Observable<Boolean> toggle(String restaurantId, String restaurantName) {

        return this.getSession().map(session -> {
            Selection newSelection = new SelectionModel().createSelection(
                restaurantId, session.getId(), restaurantName, session.getName(), session.getUrlPhoto()
            );

            Selection workmateSelection = this.getWorkmateSelection(session.getId());

            if(workmateSelection == null) {
                this.visitorGateway.addSelection(newSelection);

            } else {
                if(workmateSelection.getRestaurantId().equals(restaurantId)) {
                    this.visitorGateway.removeSelection(workmateSelection.getId());

                } else {
                    this.visitorGateway.removeSelection(workmateSelection.getId());
                    this.visitorGateway.addSelection(newSelection);
                }
            }
            return true;
        });
    }

    private List<Selection> getSelections() {
        List<Selection> selectionsResults = new ArrayList<>();
        this.visitorGateway.getSelections()
                .subscribe(selections -> selectionsResults.addAll(selections));
        return selectionsResults;
    }

    private Selection getWorkmateSelection(String workmateId) {
        List<Selection> selectionsResults = this.getSelections();
        if(!selectionsResults.isEmpty()) {
            for(Selection selection: selectionsResults) {
                if(selection.getWorkmateId().equals(workmateId))
                    return selection;
            }
        }
        return null;
    }


    private Observable<Workmate> getSession() {
        return this.sessionGateway.getSession();
    }
}

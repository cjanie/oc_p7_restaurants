package com.android.go4lunch.usecases;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class IsTheCurrentSelectionUseCase {

    private VisitorGateway visitorGateway;

    private SessionGateway sessionGateway;

    public IsTheCurrentSelectionUseCase(VisitorGateway visitorGateway, SessionGateway sessionGateway) {
        this.visitorGateway = visitorGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<Boolean> handle(String restaurantId) {
        return Observable.just(
                this.getSessionSelection() != null
                && this.getSessionSelection().getRestaurantId().equals(restaurantId)
        );
    }

    private Workmate getSession() {
        List<Workmate> workmateSessionResults = new ArrayList<>();
        this.sessionGateway.getSession().subscribe(workmateSessionResults::add);

        if(workmateSessionResults.isEmpty())
            return null;
        else return workmateSessionResults.get(0);
    }

    private List<Selection> getSelections() {
        List<Selection> selectionsResults = new ArrayList<>();
        this.visitorGateway.getSelections().subscribe(selectionsResults::addAll);
        return selectionsResults;
    }

    private Selection getSessionSelection() {
        List<Selection> selectionsResults = this.getSelections();
        Workmate workmateSession = this.getSession();
        if(workmateSession != null) {
            if(!selectionsResults.isEmpty()) {
                for(Selection selection: selectionsResults) {
                    if(selection.getWorkmateId().equals(workmateSession.getId()))
                        return selection;
                }
            }
        }
        return null;
    }
}

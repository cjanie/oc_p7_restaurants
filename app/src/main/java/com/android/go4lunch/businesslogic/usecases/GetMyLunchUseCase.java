package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class GetMyLunchUseCase {

    private SessionGateway sessionGateway;

    private VisitorGateway visitorGateway;

    private BehaviorSubject<Restaurant> myLunchSubject;

    public GetMyLunchUseCase(SessionGateway sessionGateway, VisitorGateway visitorGateway) {
        this.sessionGateway = sessionGateway;
        this.visitorGateway = visitorGateway;
        this.myLunchSubject = BehaviorSubject.create();
    }

    public Observable<Restaurant> handle() {
        return this.visitorGateway.getSelections().flatMap(selections -> {
            this.findMyLunch(selections);
            return this.myLunchSubject.hide();
        });
    }

    private void findMyLunch(List<Selection> selections) {
        this.sessionGateway.getSession().subscribe(session -> {
            if(!selections.isEmpty()) {
                for(Selection s: selections) {
                    if(s.getWorkmateId() == session.getId()) {
                        Restaurant selected = new Restaurant(s.getRestaurantName(), s.getRestaurantAddress());
                        selected.setId(s.getRestaurantId());
                        selected.setPhone(s.getRestaurantPhone());
                        selected.setWebSite(s.getRestaurantWebSite());
                        selected.setPhotoUrl(s.getRestaurantUrlPhoto());
                        this.myLunchSubject.onNext(selected);
                    }
                }
            }
        });
    }
}

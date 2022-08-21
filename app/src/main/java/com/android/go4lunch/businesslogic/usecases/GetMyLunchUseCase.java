package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
                for(Selection selection: selections) {
                    if(selection.getWorkmateId().equals(session.getId())) {
                        Restaurant selectedRestaurant = new Restaurant(selection.getRestaurantName(), selection.getRestaurantAddress());
                        selectedRestaurant.setId(selection.getRestaurantId());
                        selectedRestaurant.setPhone(selection.getRestaurantPhone());
                        selectedRestaurant.setWebSite(selection.getRestaurantWebSite());
                        selectedRestaurant.setPhotoUrl(selection.getRestaurantUrlPhoto());
                        System.out.println("findMyLunch : " + selectedRestaurant.getName());
                        this.myLunchSubject.onNext(selectedRestaurant);
                    }
                }
            }
        });
    }

}

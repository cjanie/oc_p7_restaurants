package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Notification;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ReceiveNotificationsUseCase {

    private VisitorGateway visitorGateway;

    private SessionGateway sessionGateway;

    public ReceiveNotificationsUseCase(
            VisitorGateway visitorGateway,
            SessionGateway sessionGateway
    ) {
        this.visitorGateway = visitorGateway;
        this.sessionGateway = sessionGateway;
    }

    public Observable<List<Notification>> handle() {

        return this.getSelectionsFilteredFromSession()
                .map(selections -> this.formatNotifications(selections));
    }

    private Observable<List<Selection>> getSelectionsFilteredFromSession() {
        return this.visitorGateway.getSelections()
                .flatMap(selections -> filterFromSession(selections));
    }

    private Observable<List<Selection>> filterFromSession(List<Selection> selections) {
        return this.sessionGateway.getSession()
                .map(session -> {
                    List<Selection> filteredSelections = new ArrayList<>();
                    if(!selections.isEmpty()) {
                        for(Selection selection: selections) {
                            if(!selection.getWorkmateId().equals(session.getId())) {
                                filteredSelections.add(selection);
                            }
                        }
                    }
                    return filteredSelections;
                });
    }

    private List<Notification> formatNotifications(List<Selection> selections) {
        List<Notification> notifications = new ArrayList<>();
        if(!selections.isEmpty()) {
            for(int i=0; i<selections.size(); i++) {
                Notification notification = new Notification(i, selections.get(i));
                notifications.add(notification);
            }
        }
        return notifications;
    }

}
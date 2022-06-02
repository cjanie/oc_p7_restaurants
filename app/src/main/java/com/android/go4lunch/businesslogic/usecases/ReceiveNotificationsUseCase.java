package com.android.go4lunch.businesslogic.usecases;

import com.android.go4lunch.businesslogic.entities.Notification;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ReceiveNotificationsUseCase {

    private VisitorGateway visitorGateway;

    public ReceiveNotificationsUseCase(VisitorGateway visitorGateway) {
        this.visitorGateway = visitorGateway;
    }

    public Observable<List<Notification>> handle(Selection sender) {
        return this.createNotifications(sender);
    }

    public Observable<List<Notification>> createNotifications(Selection sender) {
        return this.visitorGateway.getSelections()
                .map(selections -> {
                    List<Notification> notifications = new ArrayList<>();
                    if(!selections.isEmpty()) {
                        for(Selection selection: selections) {
                            if(!selection.getWorkmateId().equals(sender.getWorkmateId())) {
                                Notification notification = new Notification();
                                notification.setReceiver(selection);
                                notification.setSender(sender);
                                notifications.add(notification);
                            }

                        }

                    }

                    return notifications;
                });
    }

}

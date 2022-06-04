package com.android.go4lunch.businesslogic.usecases;


import com.android.go4lunch.businesslogic.entities.Notification;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.in_memory_gateways.InMemorySessionGateway;
import com.android.go4lunch.in_memory_gateways.InMemoryVisitorGateway;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReceiveNotificationsUseCaseTest {

    @Test
    public void notifyWorkmatesWithSelectionWhenUserHasSelectedARestaurant() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection = new Selection("resto1", "workmate1");
        Selection selection2 = new Selection("resto2", "workmate2");
        visitorGateway.setSelections(Arrays.asList(selection, selection2));

        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Cyril");
        session.setId("workmate2");
        sessionGateway.setSession(session);


        List<Notification> notificationsResults = new ArrayList<>();

        new ReceiveNotificationsUseCase(visitorGateway, sessionGateway).handle()
                .subscribe(notifications -> notificationsResults.addAll(notifications));

        assert(!notificationsResults.isEmpty());
    }

    @Test
    public void hasNothingToSendWhenUserHasSelectedARestaurantButTheOthersHaveNot() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection newSelection = new Selection("resto2", "workmate2");
        visitorGateway.setSelections(Arrays.asList(newSelection));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Cyril");
        session.setId("workmate2");
        sessionGateway.setSession(session);
        List<Notification> notificationsResults = new ArrayList<>();

        new ReceiveNotificationsUseCase(visitorGateway, sessionGateway).handle()
                .subscribe(notification -> notificationsResults.addAll(notification));

        assert(notificationsResults.isEmpty());
    }

    @Test
    public void sendsNothingToCurrentSession() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection = new Selection("resto2", "workmate2");
        visitorGateway.setSelections(Arrays.asList(selection));
        InMemorySessionGateway sessionGateway = new InMemorySessionGateway();
        Workmate session = new Workmate("Cyril");
        session.setId("workmate2");
        sessionGateway.setSession(session);

        List<Notification> notificationsResults = new ArrayList<>();
        Selection newSelection = new Selection("resto2", "workmate2");
        new ReceiveNotificationsUseCase(visitorGateway, sessionGateway).handle()
                .subscribe(notification -> notificationsResults.addAll(notification));

        assert(notificationsResults.isEmpty());
    }
}

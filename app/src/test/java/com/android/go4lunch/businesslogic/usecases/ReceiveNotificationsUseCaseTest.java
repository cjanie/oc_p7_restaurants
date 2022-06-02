package com.android.go4lunch.businesslogic.usecases;


import com.android.go4lunch.businesslogic.entities.Notification;
import com.android.go4lunch.businesslogic.entities.Selection;
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
        visitorGateway.setSelections(Arrays.asList(selection));

        List<Notification> notificationsResults = new ArrayList<>();
        Selection newSelection = new Selection("resto2", "workmate2");
        new ReceiveNotificationsUseCase(visitorGateway).handle(newSelection)
                .subscribe(notifications -> notificationsResults.addAll(notifications));

        assert(!notificationsResults.isEmpty());
    }

    @Test
    public void hasNothingToSendWhenUserHasSelectedARestaurantButTheOthersHaveNot() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();

        List<Notification> notificationsResults = new ArrayList<>();
        Selection newSelection = new Selection("resto2", "workmate2");
        new ReceiveNotificationsUseCase(visitorGateway).handle(newSelection)
                .subscribe(notification -> notificationsResults.addAll(notification));

        assert(notificationsResults.isEmpty());
    }

    @Test
    public void sendsNothingToCurrentSession() {
        InMemoryVisitorGateway visitorGateway = new InMemoryVisitorGateway();
        Selection selection = new Selection("resto2", "workmate2");
        visitorGateway.setSelections(Arrays.asList(selection));

        List<Notification> notificationsResults = new ArrayList<>();
        Selection newSelection = new Selection("resto2", "workmate2");
        new ReceiveNotificationsUseCase(visitorGateway).handle(newSelection)
                .subscribe(notification -> notificationsResults.addAll(notification));

        assert(notificationsResults.isEmpty());
    }
}

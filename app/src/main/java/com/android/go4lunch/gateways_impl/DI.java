package com.android.go4lunch.gateways_impl;

import android.content.Context;

import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.gateways.WorkmateGateway;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class DI {

    private static RestaurantGateway restaurantGateway;

    private static WorkmateGateway workmateGateway;

    private static InMemoryVisitorsGateway visitorsGateway;

    private static SessionGateway sessionGateway = new SessionGatewayImpl();

    public static RestaurantGateway restaurantGatewayInstance() {

        if(restaurantGateway == null) {
            GoogleMapsHttpClientProvider httpClientProvider = new GoogleMapsHttpClientProvider();
            RestaurantRepository restaurantRepository = new RestaurantRepository(httpClientProvider);

            restaurantGateway = new RestaurantGatewayImpl(restaurantRepository);

            DistanceRepository distanceRepository = new DistanceRepository(httpClientProvider);
            DistanceGatewayImpl distanceGateway = new DistanceGatewayImpl(distanceRepository);
        }
        return restaurantGateway;
    }

    public static WorkmateGateway workmateGatewayInstance(Context context) {
        if(workmateGateway == null) {
            FirebaseApp.initializeApp(context);
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            workmateGateway = new WorkmateGatewayImpl(database);
        }
        return workmateGateway;
    }

    public static VisitorsGateway visitorsGatewayInstance() {
        if(visitorsGateway == null) {
            visitorsGateway = new InMemoryVisitorsGateway();
        }
        visitorsGateway.setSelections(new Mock().selections());
        return visitorsGateway;
    }

    public static SessionGateway sessionGatewayInstance() {
        return sessionGateway;
    }
}

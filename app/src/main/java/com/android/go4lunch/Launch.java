package com.android.go4lunch;

import android.app.Application;
import android.content.Context;

import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.gateways.VisitorsGateway;
import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.gateways_impl.DI;
import com.android.go4lunch.gateways_impl.DistanceGatewayImpl;
import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.gateways_impl.RestaurantGatewayImpl;
import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.gateways_impl.WorkmateGatewayImpl;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.ui.viewmodels.factories.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.RestaurantDetailsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.RestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.WorkmatesViewModelFactory;
import com.android.go4lunch.usecases.GetRestaurantByIdUseCase;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.LikeUseCase;
import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;
import com.android.go4lunch.usecases.GetRestaurantsForMapUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class Launch extends Application {

    // view models factories
    private final MapViewModelFactory mapViewModelFactory;

    private final RestaurantsViewModelFactory restaurantsViewModelFactory;

    private RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory;

    private WorkmatesViewModelFactory workmatesViewModelFactory;

    private final RestaurantGateway restaurantGateway;

    private WorkmateGatewayImpl workmateGateway;

    private final VisitorsGateway visitorsGateway;

    private final SessionGateway sessionGateway;


    public Launch() {
        // DEPENDENCIES
        this.restaurantGateway = DI.restaurantGatewayInstance();
        this.visitorsGateway = DI.visitorsGatewayInstance();
        this.sessionGateway = DI.sessionGatewayInstance();

        RealTimeProvider timeProvider = new RealTimeProvider();
        RealDateProvider dateProvider = new RealDateProvider();
        // USE CASES

        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = new GetRestaurantsForMapUseCase(this.restaurantGateway);
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = new GetRestaurantsForListUseCase(this.restaurantGateway);


        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(this.visitorsGateway);

        // VIEW MODELS FACTORIES
        this.mapViewModelFactory = new MapViewModelFactory(getRestaurantsForMapUseCase);
        this.restaurantsViewModelFactory = new RestaurantsViewModelFactory(
                getRestaurantsForListUseCase,
                getRestaurantVisitorsUseCase,
                timeProvider,
                dateProvider
        );
    }

    public MapViewModelFactory mapViewModelFactory() {
        return this.mapViewModelFactory;
    }

    public RestaurantsViewModelFactory restaurantsViewModelFactory() {
        return this.restaurantsViewModelFactory;
    }

    public RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory(Context context) {
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(this.sessionGateway);
        LikeUseCase likeUseCase = new LikeUseCase(this.visitorsGateway);
        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(this.visitorsGateway);

        this.workmateGateway = (WorkmateGatewayImpl) DI.workmateGatewayInstance(context);
        GetWorkmateByIdUseCase getWorkmateByIdUseCase = new GetWorkmateByIdUseCase(workmateGateway);


        this.restaurantDetailsViewModelFactory = new RestaurantDetailsViewModelFactory(
                getSessionUseCase,
                likeUseCase,
                new GetRestaurantVisitorsUseCase(this.visitorsGateway),
                isTheCurrentSelectionUseCase,
                getWorkmateByIdUseCase
        );
        return this.restaurantDetailsViewModelFactory;
    }

    public WorkmatesViewModelFactory workmatesViewModelFactory(Context context) {
        this.workmateGateway = (WorkmateGatewayImpl) DI.workmateGatewayInstance(context);
        GetWorkmatesUseCase getWorkmatesUseCase = new GetWorkmatesUseCase(this.workmateGateway);

        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(this.visitorsGateway);
        GetRestaurantByIdUseCase getRestaurantByIdUseCase = new GetRestaurantByIdUseCase(this.restaurantGateway);

        this.workmatesViewModelFactory = new WorkmatesViewModelFactory(
                getWorkmatesUseCase,
                getWorkmateSelectionUseCase,
                getRestaurantByIdUseCase
        );
        return this.workmatesViewModelFactory;

    }

    private FirebaseFirestore initDatabase(Context context) {
        FirebaseApp.initializeApp(context);
        return FirebaseFirestore.getInstance();
    }




}

package com.android.go4lunch;

import android.app.Application;

import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.gateways_impl.DistanceGatewayImpl;
import com.android.go4lunch.gateways_impl.HistoricOfSelectionsGatewayImpl;
import com.android.go4lunch.gateways_impl.RestaurantGatewayImpl;
import com.android.go4lunch.gateways_impl.SelectionGatewayImpl;
import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.ui.viewmodels.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModelFactory;
import com.android.go4lunch.usecases.ToggleSelectionUseCase;
import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;
import com.android.go4lunch.usecases.GetRestaurantsForMapUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

public class Launch extends Application {

    // view models factories
    private final MapViewModelFactory mapViewModelFactory;

    private final RestaurantsViewModelFactory restaurantsViewModelFactory;

    private final RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory;

    public Launch() {
        // DEPENDENCIES
        GoogleMapsHttpClientProvider httpClientProvider = new GoogleMapsHttpClientProvider();
        RestaurantRepository restaurantRepository = new RestaurantRepository(httpClientProvider);
        DistanceRepository distanceRepository = new DistanceRepository(httpClientProvider);
        RestaurantGatewayImpl restaurantGateway = new RestaurantGatewayImpl(restaurantRepository);
        DistanceGatewayImpl distanceGateway = new DistanceGatewayImpl(distanceRepository);
        SelectionGatewayImpl selectionGateway = new SelectionGatewayImpl();
        SessionGatewayImpl sessionGateway = new SessionGatewayImpl();
        HistoricOfSelectionsGatewayImpl historicOfSelectionsGateway = new HistoricOfSelectionsGatewayImpl();
        RealTimeProvider timeProvider = new RealTimeProvider();
        RealDateProvider dateProvider = new RealDateProvider();
        // USE CASES
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = new GetRestaurantsForMapUseCase(restaurantGateway);
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = new GetRestaurantsForListUseCase(
                restaurantGateway,
                timeProvider,
                dateProvider,
                distanceGateway,
                selectionGateway,
                historicOfSelectionsGateway
        );
        ToggleSelectionUseCase toggleSelectionUseCase = new ToggleSelectionUseCase(selectionGateway);
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(sessionGateway);
        // VIEW MODELS FACTORIES
        this.mapViewModelFactory = new MapViewModelFactory(getRestaurantsForMapUseCase);
        this.restaurantsViewModelFactory = new RestaurantsViewModelFactory(getRestaurantsForListUseCase);
        this.restaurantDetailsViewModelFactory = new RestaurantDetailsViewModelFactory(toggleSelectionUseCase, getSessionUseCase);

    }

    public MapViewModelFactory mapViewModelFactory() {
        return this.mapViewModelFactory;
    }

    public RestaurantsViewModelFactory restaurantsViewModelFactory() {
        return this.restaurantsViewModelFactory;
    }

    public RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory() {
        return this.restaurantDetailsViewModelFactory;
    }

}

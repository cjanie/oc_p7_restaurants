package com.android.go4lunch;

import android.app.Application;

import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.gateways_impl.DistanceGatewayImpl;
import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.gateways_impl.RestaurantGatewayImpl;
import com.android.go4lunch.gateways_impl.SelectionGatewayImpl;
import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.gateways_impl.VisitorsGatewayImpl;
import com.android.go4lunch.gateways_impl.WorkmateGatewayImpl;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.ui.viewmodels.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.WorkmatesViewModelFactory;
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

import java.util.Arrays;

public class Launch extends Application {

    // view models factories
    private final MapViewModelFactory mapViewModelFactory;

    private final RestaurantsViewModelFactory restaurantsViewModelFactory;

    private final RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory;

    private final WorkmatesViewModelFactory workmatesViewModelFactory;

    public Launch() {
        // DEPENDENCIES
        GoogleMapsHttpClientProvider httpClientProvider = new GoogleMapsHttpClientProvider();
        RestaurantRepository restaurantRepository = new RestaurantRepository(httpClientProvider);
        DistanceRepository distanceRepository = new DistanceRepository(httpClientProvider);
        RestaurantGatewayImpl restaurantGateway = new RestaurantGatewayImpl(restaurantRepository);
        DistanceGatewayImpl distanceGateway = new DistanceGatewayImpl(distanceRepository);
        SessionGatewayImpl sessionGateway = new SessionGatewayImpl();
        //VisitorsGatewayImpl visitorsGateway = new VisitorsGatewayImpl();

        InMemoryVisitorsGateway visitorsGateway = new InMemoryVisitorsGateway();
        visitorsGateway.setSelections(new Mock().selections());
        WorkmateGatewayImpl workmateGateway = new WorkmateGatewayImpl();
        RealTimeProvider timeProvider = new RealTimeProvider();
        RealDateProvider dateProvider = new RealDateProvider();
        // USE CASES
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(sessionGateway);
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = new GetRestaurantsForMapUseCase(restaurantGateway);
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = new GetRestaurantsForListUseCase(restaurantGateway);

        LikeUseCase likeUseCase = new LikeUseCase(visitorsGateway);

        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorsGateway);

        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway);

        GetWorkmatesUseCase getWorkmatesUseCase = new GetWorkmatesUseCase(workmateGateway);

        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway);

        GetWorkmateByIdUseCase getWorkmateByIdUseCase = new GetWorkmateByIdUseCase(workmateGateway);

        GetRestaurantByIdUseCase getRestaurantByIdUseCase = new GetRestaurantByIdUseCase(restaurantGateway);

        // VIEW MODELS FACTORIES
        this.mapViewModelFactory = new MapViewModelFactory(getRestaurantsForMapUseCase);
        this.restaurantsViewModelFactory = new RestaurantsViewModelFactory(
                getRestaurantsForListUseCase,
                getRestaurantVisitorsUseCase,
                timeProvider,
                dateProvider
        );
        this.restaurantDetailsViewModelFactory = new RestaurantDetailsViewModelFactory(
                getSessionUseCase,
                likeUseCase,
                getRestaurantVisitorsUseCase,
                isTheCurrentSelectionUseCase,
                getWorkmateByIdUseCase
        );
        this.workmatesViewModelFactory = new WorkmatesViewModelFactory(
                getWorkmatesUseCase,
                getWorkmateSelectionUseCase,
                getRestaurantByIdUseCase
        );
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

    public WorkmatesViewModelFactory workmatesViewModelFactory() {
        return this.workmatesViewModelFactory;
    }


}

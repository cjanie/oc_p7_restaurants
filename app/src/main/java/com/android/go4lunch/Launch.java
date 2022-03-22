package com.android.go4lunch;

import android.app.Application;

import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.gateways_impl.DistanceGatewayImpl;
import com.android.go4lunch.gateways_impl.HistoricOfSelectionsGatewayImpl;
import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.gateways_impl.RestaurantGatewayImpl;
import com.android.go4lunch.gateways_impl.SelectionGatewayImpl;
import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.models.Selection;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.ui.viewmodels.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.RestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.WorkmatesViewModelFactory;
import com.android.go4lunch.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.usecases.GetWorkmateSelectionUseCase;
import com.android.go4lunch.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.usecases.LikeForLunchUseCase;
import com.android.go4lunch.usecases.GetRestaurantsForListUseCase;
import com.android.go4lunch.usecases.GetRestaurantsForMapUseCase;
import com.android.go4lunch.usecases.GetSessionUseCase;

import java.util.ArrayList;
import java.util.List;

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
        SelectionGatewayImpl selectionGateway = new SelectionGatewayImpl();
        SessionGatewayImpl sessionGateway = new SessionGatewayImpl();
        InMemoryVisitorsGateway inMemoryVisitorsGateway = new InMemoryVisitorsGateway();
        List<Selection> selections = new ArrayList<>();
        Selection selection1= new Selection("1", "Chez Lol", "2", "Cyril");
        selections.add(selection1);
        Selection selection2= new Selection("1", "Chez Lol", "3", "Sylvaine");
        selections.add(selection2);
        inMemoryVisitorsGateway.setSelections(selections);
        HistoricOfSelectionsGatewayImpl historicOfSelectionsGateway = new HistoricOfSelectionsGatewayImpl();
        RealTimeProvider timeProvider = new RealTimeProvider();
        RealDateProvider dateProvider = new RealDateProvider();
        // USE CASES
        GetSessionUseCase getSessionUseCase = new GetSessionUseCase(sessionGateway);
        GetRestaurantsForMapUseCase getRestaurantsForMapUseCase = new GetRestaurantsForMapUseCase(restaurantGateway);
        GetRestaurantsForListUseCase getRestaurantsForListUseCase = new GetRestaurantsForListUseCase(
                restaurantGateway,
                timeProvider,
                dateProvider,
                distanceGateway,
                selectionGateway,
                historicOfSelectionsGateway
        );

        LikeForLunchUseCase likeForLunchUseCase = new LikeForLunchUseCase(
                selectionGateway,
                inMemoryVisitorsGateway
        );

        GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(inMemoryVisitorsGateway);

        IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(inMemoryVisitorsGateway);

        GetWorkmateSelectionUseCase getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(inMemoryVisitorsGateway);
        // VIEW MODELS FACTORIES
        this.mapViewModelFactory = new MapViewModelFactory(getRestaurantsForMapUseCase);
        this.restaurantsViewModelFactory = new RestaurantsViewModelFactory(getRestaurantsForListUseCase);
        this.restaurantDetailsViewModelFactory = new RestaurantDetailsViewModelFactory(
                getSessionUseCase,
                likeForLunchUseCase,
                getRestaurantVisitorsUseCase,
                isTheCurrentSelectionUseCase
        );
        this.workmatesViewModelFactory = new WorkmatesViewModelFactory(
                getWorkmateSelectionUseCase
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

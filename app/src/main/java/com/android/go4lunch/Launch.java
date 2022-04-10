package com.android.go4lunch;

import android.app.Application;

import com.android.go4lunch.apis.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.apis.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.apis.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.gateways.DistanceGateway;
import com.android.go4lunch.gateways.RestaurantGateway;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.gateways_impl.DistanceGatewayImpl;
import com.android.go4lunch.gateways_impl.InMemoryVisitorsGateway;
import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.gateways_impl.RestaurantGatewayImpl;
import com.android.go4lunch.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.gateways_impl.WorkmateGatewayImpl;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.providers.TimeProvider;
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

    // Date time providers
    private TimeProvider timeProvider;
    private DateProvider dateProvider;

    // Dependencies
    private FirebaseFirestore database;
    private GoogleMapsHttpClientProvider googleMapsHttpClientProvider;

    // Gateways
    private RestaurantGateway restaurantGateway;
    private DistanceGateway distanceGateway;
    private WorkmateGateway workmateGateway;
    private InMemoryVisitorsGateway visitorsGateway;
    private SessionGateway sessionGateway;

    // Use cases
    private GetRestaurantsForMapUseCase getRestaurantsForMapUseCase;
    private GetRestaurantsForListUseCase getRestaurantsForListUseCase;
    private GetWorkmatesUseCase getWorkmatesUseCase;
    private GetWorkmateSelectionUseCase getWorkmateSelectionUseCase;
    private GetWorkmateByIdUseCase getWorkmateByIdUseCase;
    private GetRestaurantByIdUseCase getRestaurantByIdUseCase;
    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;
    private GetSessionUseCase getSessionUseCase;
    private LikeUseCase likeUseCase;
    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;

    // view models factories
    private MapViewModelFactory mapViewModelFactory;
    private RestaurantsViewModelFactory restaurantsViewModelFactory;
    private RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory;
    private WorkmatesViewModelFactory workmatesViewModelFactory;


    // INSTANTIATIONS

    // Date time providers
    private synchronized TimeProvider timeProvider() {
        if(this.timeProvider == null) {
            this.timeProvider = new RealTimeProvider();
        }
        return this.timeProvider;
    }

    private synchronized DateProvider dateProvider() {
        if(this.dateProvider == null) {
            this.dateProvider = new RealDateProvider();
        }
        return this.dateProvider;
    }

    // Dependencies
    private synchronized FirebaseFirestore database() {
        if(this.database == null) {
            FirebaseApp.initializeApp(this.getApplicationContext());
            this.database = FirebaseFirestore.getInstance();
        }
        return this.database;
    }

    private synchronized GoogleMapsHttpClientProvider googleMapsHttpClientProvider() {
        if(this.googleMapsHttpClientProvider == null) {
            this.googleMapsHttpClientProvider = new GoogleMapsHttpClientProvider();
        }
        return this.googleMapsHttpClientProvider;
    }

    // Gateways
    private synchronized RestaurantGateway restaurantGateway() {
        if(this.restaurantGateway == null) {
            RestaurantRepository restaurantRepository = new RestaurantRepository(googleMapsHttpClientProvider());
            this.restaurantGateway = new RestaurantGatewayImpl(restaurantRepository);
        }
        return this.restaurantGateway;
    }

    private synchronized DistanceGateway distanceGateway() {
        if(this.distanceGateway == null) {
            DistanceRepository distanceRepository = new DistanceRepository(googleMapsHttpClientProvider());
            distanceGateway = new DistanceGatewayImpl(distanceRepository);

        }
        return this.distanceGateway;
    }

    private synchronized WorkmateGateway workmateGateway() {
        if(this.workmateGateway == null) {
            this.workmateGateway = new WorkmateGatewayImpl(database());
        }
        return this.workmateGateway;
    }

    private synchronized InMemoryVisitorsGateway visitorsGateway() {
        if(this.visitorsGateway == null) {
            this.visitorsGateway = new InMemoryVisitorsGateway();
            this.visitorsGateway.setSelections(new Mock().selections());
        }
        return visitorsGateway;
    }

    private synchronized SessionGateway sessionGateway() {
        if(this.sessionGateway == null) {
            this.sessionGateway = new SessionGatewayImpl();
        }
        return this.sessionGateway;
    }

    // Use cases
    private synchronized GetRestaurantsForMapUseCase getGetRestaurantsForMapUseCase() {
        if(this.getRestaurantsForMapUseCase == null) {
            this.getRestaurantsForMapUseCase = new GetRestaurantsForMapUseCase(restaurantGateway());
        }
        return this.getRestaurantsForMapUseCase;
    }

    private synchronized GetRestaurantsForListUseCase getGetRestaurantsForListUseCase() {
        if(this.getRestaurantsForListUseCase == null) {
            this.getRestaurantsForListUseCase = new GetRestaurantsForListUseCase(restaurantGateway());
        }
        return this.getRestaurantsForListUseCase;
    }

    private synchronized GetWorkmatesUseCase getWorkmatesUseCase() {
        if(this.getWorkmatesUseCase == null) {
            this.getWorkmatesUseCase = new GetWorkmatesUseCase(workmateGateway());
        }
        return this.getWorkmatesUseCase;
    }

    private synchronized GetWorkmateSelectionUseCase getWorkmateSelectionUseCase() {
        if(this.getWorkmateSelectionUseCase == null) {
            this.getWorkmateSelectionUseCase = new GetWorkmateSelectionUseCase(visitorsGateway());
        }
        return this.getWorkmateSelectionUseCase;
    }

    private synchronized GetWorkmateByIdUseCase getWorkmateByIdUseCase() {
        if(this.getWorkmateByIdUseCase == null) {
            this.getWorkmateByIdUseCase = new GetWorkmateByIdUseCase(workmateGateway());
        }
        return this.getWorkmateByIdUseCase;
    }

    private synchronized GetRestaurantByIdUseCase getRestaurantByIdUseCase() {
        if(this.getRestaurantByIdUseCase == null) {
            this.getRestaurantByIdUseCase = new GetRestaurantByIdUseCase(restaurantGateway());
        }
        return this.getRestaurantByIdUseCase;
    }

    private synchronized GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase() {
        if(this.getRestaurantVisitorsUseCase == null) {
            this.getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorsGateway());
        }
        return this.getRestaurantVisitorsUseCase;
    }

    private synchronized GetSessionUseCase getSessionUseCase() {
        if(this.getSessionUseCase == null) {
            this.getSessionUseCase = new GetSessionUseCase(sessionGateway());
        }
        return this.getSessionUseCase;
    }

    private synchronized LikeUseCase likeUseCase() {
        if(this.likeUseCase == null) {
            this.likeUseCase = new LikeUseCase(visitorsGateway());
        }
        return this.likeUseCase;
    }

    private synchronized IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase() {
        if(isTheCurrentSelectionUseCase == null) {
            this.isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(visitorsGateway());
        }
        return this.isTheCurrentSelectionUseCase;
    }

    // View model factories

    public synchronized MapViewModelFactory mapViewModelFactory() {
        if(this.mapViewModelFactory == null) {
            this.mapViewModelFactory = new MapViewModelFactory(this.getGetRestaurantsForMapUseCase());
        }
        return this.mapViewModelFactory;
    }

    public synchronized RestaurantsViewModelFactory restaurantsViewModelFactory() {
        if(this.restaurantsViewModelFactory == null) {
            this.restaurantsViewModelFactory = new RestaurantsViewModelFactory(
                    this.getGetRestaurantsForListUseCase(),
                    this.getRestaurantVisitorsUseCase(),
                    this.timeProvider(),
                    this.dateProvider()
            );
        }
        return this.restaurantsViewModelFactory;
    }

    public synchronized RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory() {
        if(this.restaurantDetailsViewModelFactory == null) {
            this.restaurantDetailsViewModelFactory = new RestaurantDetailsViewModelFactory(
                    getSessionUseCase(),
                    likeUseCase(),
                    getRestaurantVisitorsUseCase(),
                    isTheCurrentSelectionUseCase(),
                    getWorkmateByIdUseCase()
            );
        }
        return this.restaurantDetailsViewModelFactory;
    }

    public synchronized WorkmatesViewModelFactory workmatesViewModelFactory() {
        if(this.workmatesViewModelFactory == null) {
            this.workmatesViewModelFactory = new WorkmatesViewModelFactory(
                    getWorkmatesUseCase(),
                    getWorkmateSelectionUseCase(),
                    getRestaurantByIdUseCase()
            );
        }
        return this.workmatesViewModelFactory;
    }

}

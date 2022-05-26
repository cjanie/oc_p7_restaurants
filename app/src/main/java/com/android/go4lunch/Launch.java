package com.android.go4lunch;

import android.app.Application;

import com.android.go4lunch.data.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.data.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.data.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.data.gateways_impl.DistanceGatewayImpl;
import com.android.go4lunch.data.gateways_impl.LikeGatewayImpl;
import com.android.go4lunch.data.gateways_impl.RestaurantGatewayImpl;
import com.android.go4lunch.data.gateways_impl.SessionGatewayImpl;
import com.android.go4lunch.data.gateways_impl.VisitorGatewayImpl;
import com.android.go4lunch.data.gateways_impl.WorkmateGatewayImpl;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.ui.viewmodels.factories.MainViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.RestaurantDetailsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.RestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.SignInViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.WorkmatesViewModelFactory;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.GetWorkmateByIdUseCase;
import com.android.go4lunch.businesslogic.usecases.GetWorkmatesUseCase;
import com.android.go4lunch.businesslogic.usecases.GoForLunchUseCase;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsForListUseCase;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantsNearbyUseCase;
import com.android.go4lunch.businesslogic.usecases.GetSessionUseCase;
import com.android.go4lunch.businesslogic.usecases.IsInFavoritesRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.IsTheCurrentSelectionUseCase;
import com.android.go4lunch.businesslogic.usecases.AddLikeUseCase;
import com.android.go4lunch.businesslogic.usecases.SaveWorkmateUseCase;
import com.android.go4lunch.businesslogic.usecases.SignOutUseCase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Launch extends Application {

    // Date time providers
    private TimeProvider timeProvider;
    private DateProvider dateProvider;

    // Dependencies
    private FirebaseAuth auth;
    private FirebaseFirestore database;
    private GoogleMapsHttpClientProvider googleMapsHttpClientProvider;

    // Gateways
    private RestaurantGateway restaurantGateway;
    private DistanceGateway distanceGateway;
    private WorkmateGateway workmateGateway;
    private VisitorGateway visitorGateway;
    private SessionGateway sessionGateway;
    private LikeGateway likeGateway;

    // Use cases
    private GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;
    private GetRestaurantsForListUseCase getRestaurantsForListUseCase;
    private GetWorkmatesUseCase getWorkmatesUseCase;
    private GetWorkmateByIdUseCase getWorkmateByIdUseCase;
    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;
    private GetSessionUseCase getSessionUseCase;
    private GoForLunchUseCase goForLunchUseCase;
    private SaveWorkmateUseCase saveWorkmateUseCase;
    private SignOutUseCase signOutUseCase;
    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;
    private AddLikeUseCase addLikeUseCase;
    private IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase;
    private GetNumberOfLikesPerRestaurantUseCase getNumberOfLikesPerRestaurantUseCase;

    // view models factories
    private MapViewModelFactory mapViewModelFactory;
    private RestaurantsViewModelFactory restaurantsViewModelFactory;
    private RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory;
    private WorkmatesViewModelFactory workmatesViewModelFactory;
    private SignInViewModelFactory signInViewModelFactory;
    private MainViewModelFactory mainViewModelFactory;


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
    private synchronized FirebaseAuth auth() {
        if(this.auth == null) {
            FirebaseApp.initializeApp(this.getApplicationContext());
            this.auth = FirebaseAuth.getInstance();
        }
        return this.auth;
    }

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

    private synchronized VisitorGateway visitorGateway() {
        if(this.visitorGateway == null) {
            this.visitorGateway = new VisitorGatewayImpl(this.database());
        }
        return visitorGateway;
    }

    private synchronized SessionGateway sessionGateway() {
        if(this.sessionGateway == null) {
            this.sessionGateway = new SessionGatewayImpl(this.auth());
        }
        return this.sessionGateway;
    }

    private synchronized LikeGateway likeGateway() {
        if(this.likeGateway == null) {
            this.likeGateway = new LikeGatewayImpl(this.database());
        }
        return this.likeGateway;
    }

    // Use cases
    private synchronized GetRestaurantsNearbyUseCase getGetRestaurantsForMapUseCase() {
        if(this.getRestaurantsNearbyUseCase == null) {
            this.getRestaurantsNearbyUseCase = new GetRestaurantsNearbyUseCase(restaurantGateway());
        }
        return this.getRestaurantsNearbyUseCase;
    }

    private synchronized GetRestaurantsForListUseCase getGetRestaurantsForListUseCase() {
        if(this.getRestaurantsForListUseCase == null) {
            this.getRestaurantsForListUseCase = new GetRestaurantsForListUseCase(restaurantGateway());
        }
        return this.getRestaurantsForListUseCase;
    }

    private synchronized GetWorkmatesUseCase getWorkmatesUseCase() {
        if(this.getWorkmatesUseCase == null) {
            this.getWorkmatesUseCase = new GetWorkmatesUseCase(
                    this.workmateGateway(),
                    this.sessionGateway(),
                    this.visitorGateway()
            );
        }
        return this.getWorkmatesUseCase;
    }

    private synchronized GetWorkmateByIdUseCase getWorkmateByIdUseCase() {
        if(this.getWorkmateByIdUseCase == null) {
            this.getWorkmateByIdUseCase = new GetWorkmateByIdUseCase(workmateGateway());
        }
        return this.getWorkmateByIdUseCase;
    }

    private synchronized GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase() {
        if(this.getRestaurantVisitorsUseCase == null) {
            this.getRestaurantVisitorsUseCase = new GetRestaurantVisitorsUseCase(visitorGateway());
        }
        return this.getRestaurantVisitorsUseCase;
    }

    private synchronized GetSessionUseCase getSessionUseCase() {
        if(this.getSessionUseCase == null) {
            this.getSessionUseCase = new GetSessionUseCase(sessionGateway());
        }
        return this.getSessionUseCase;
    }

    private synchronized GoForLunchUseCase goForLunchUseCase() {
        if(this.goForLunchUseCase == null) {
            this.goForLunchUseCase = new GoForLunchUseCase(
                    visitorGateway(),
                    sessionGateway());
        }
        return this.goForLunchUseCase;
    }

    private synchronized SaveWorkmateUseCase saveWorkmateUseCase() {
        if(this.saveWorkmateUseCase == null) {
            this.saveWorkmateUseCase = new SaveWorkmateUseCase(this.workmateGateway());
        }
        return this.saveWorkmateUseCase;
    }

    private synchronized SignOutUseCase signOutUseCase() {
        if(this.signOutUseCase == null) {
            this.signOutUseCase = new SignOutUseCase(this.sessionGateway());
        }
        return this.signOutUseCase;
    }

    private synchronized IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase() {
        if(this.isTheCurrentSelectionUseCase == null) {
            this.isTheCurrentSelectionUseCase = new IsTheCurrentSelectionUseCase(
                    this.visitorGateway(),
                    this.sessionGateway()
            );
        }
        return this.isTheCurrentSelectionUseCase;
    }

    private synchronized AddLikeUseCase likeUseCase() {
        if(this.addLikeUseCase == null) {
            this.addLikeUseCase = new AddLikeUseCase(
                    this.likeGateway()
            );
        }
        return this.addLikeUseCase;
    }

    private synchronized IsInFavoritesRestaurantsUseCase isOneOfTheUserFavoriteRestaurants() {
        if(this.isInFavoritesRestaurantsUseCase == null) {
            this.isInFavoritesRestaurantsUseCase = new IsInFavoritesRestaurantsUseCase(
                    this.likeGateway(),
                    this.sessionGateway()
            );
        }
        return this.isInFavoritesRestaurantsUseCase;
    }

    private synchronized GetNumberOfLikesPerRestaurantUseCase getNumberOfLikesPerRestaurantUseCase() {
        if(this.getNumberOfLikesPerRestaurantUseCase == null) {
            this.getNumberOfLikesPerRestaurantUseCase = new GetNumberOfLikesPerRestaurantUseCase(
                    this.likeGateway()
            );
        }
        return this.getNumberOfLikesPerRestaurantUseCase;
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
                    goForLunchUseCase(),
                    getRestaurantVisitorsUseCase(),
                    getWorkmateByIdUseCase(),
                    isTheCurrentSelectionUseCase(),
                    likeUseCase(),
                    isOneOfTheUserFavoriteRestaurants()
            );
        }
        return this.restaurantDetailsViewModelFactory;
    }

    public synchronized WorkmatesViewModelFactory workmatesViewModelFactory() {
        if(this.workmatesViewModelFactory == null) {
            this.workmatesViewModelFactory = new WorkmatesViewModelFactory(
                    getWorkmatesUseCase()
            );
        }
        return this.workmatesViewModelFactory;
    }

    public synchronized SignInViewModelFactory signInViewModelFactory() {
        if(this.signInViewModelFactory == null) {
            this.signInViewModelFactory = new SignInViewModelFactory(
                    this.saveWorkmateUseCase()
            );
        }
        return this.signInViewModelFactory;
    }

    public synchronized MainViewModelFactory mainViewModelFactory() {
        if(this.mainViewModelFactory == null) {
            this.mainViewModelFactory = new MainViewModelFactory(
                    this.getSessionUseCase(),
                    this.signOutUseCase()
            );
        }
        return this.mainViewModelFactory;
    }

}

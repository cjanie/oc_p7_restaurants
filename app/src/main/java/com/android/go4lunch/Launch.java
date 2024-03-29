package com.android.go4lunch;

import android.app.Application;

import com.android.go4lunch.businesslogic.gateways.MyPositionGateway;
import com.android.go4lunch.businesslogic.usecases.GetDistanceFromMyPositionToRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetMyPositionUseCase;
import com.android.go4lunch.businesslogic.usecases.ReceiveNotificationsUseCase;
import com.android.go4lunch.businesslogic.usecases.SaveMyPositionUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.FilterSelectedRestaurantsUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.SearchRestaurantUseCase;
import com.android.go4lunch.data.apiGoogleMaps.repositories.DistanceRepository;
import com.android.go4lunch.data.apiGoogleMaps.GoogleMapsHttpClientProvider;
import com.android.go4lunch.data.apiGoogleMaps.repositories.RestaurantRepository;
import com.android.go4lunch.businesslogic.gateways.DistanceGateway;
import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.gateways.RestaurantGateway;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.data.gatewaysImpl.DistanceGatewayImpl;
import com.android.go4lunch.data.gatewaysImpl.InMemoryMyPositionGatewayImpl;
import com.android.go4lunch.data.gatewaysImpl.LikeGatewayImpl;
import com.android.go4lunch.data.gatewaysImpl.RestaurantGatewayImpl;
import com.android.go4lunch.data.gatewaysImpl.SessionGatewayImpl;
import com.android.go4lunch.data.gatewaysImpl.VisitorGatewayImpl;
import com.android.go4lunch.data.gatewaysImpl.WorkmateGatewayImpl;
import com.android.go4lunch.providers.DateProvider;
import com.android.go4lunch.providers.RealDateProvider;
import com.android.go4lunch.providers.RealTimeProvider;
import com.android.go4lunch.providers.TimeProvider;
import com.android.go4lunch.ui.viewmodels.Cache;
import com.android.go4lunch.ui.notifications.ShowNotificationsAction;
import com.android.go4lunch.ui.viewmodels.factories.MainViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.MapSelectedRestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.MapViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.RestaurantDetailsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.RestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.SearchViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.SelectedRestaurantsViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.SharedViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.SignInViewModelFactory;
import com.android.go4lunch.ui.viewmodels.factories.WorkmatesViewModelFactory;
import com.android.go4lunch.businesslogic.usecases.GetNumberOfLikesPerRestaurantUseCase;
import com.android.go4lunch.businesslogic.usecases.GetRestaurantVisitorsUseCase;
import com.android.go4lunch.businesslogic.usecases.workmate.GetWorkmatesUseCase;
import com.android.go4lunch.businesslogic.usecases.GoForLunchUseCase;
import com.android.go4lunch.businesslogic.usecases.restaurant.GetRestaurantsNearbyUseCase;
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
    private MyPositionGateway myPositionGateway;

    // Use cases
    private GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase;
    private GetDistanceFromMyPositionToRestaurantUseCase getDistanceFromMyPositionToRestaurantUseCase;
    private GetWorkmatesUseCase getWorkmatesUseCase;
    private GetRestaurantVisitorsUseCase getRestaurantVisitorsUseCase;
    private GetSessionUseCase getSessionUseCase;
    private GoForLunchUseCase goForLunchUseCase;
    private SaveWorkmateUseCase saveWorkmateUseCase;
    private SignOutUseCase signOutUseCase;
    private IsTheCurrentSelectionUseCase isTheCurrentSelectionUseCase;
    private AddLikeUseCase addLikeUseCase;
    private IsInFavoritesRestaurantsUseCase isInFavoritesRestaurantsUseCase;
    private GetNumberOfLikesPerRestaurantUseCase getNumberOfLikesPerRestaurantUseCase;
    private ReceiveNotificationsUseCase receiveNotificationsUseCase;
    private SaveMyPositionUseCase saveMyPositionUseCase;
    private GetMyPositionUseCase getMyPositionUseCase;
    private SearchRestaurantUseCase searchRestaurantUseCase;
    private FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase;

    // view models factories
    private MapViewModelFactory mapViewModelFactory;
    private RestaurantsViewModelFactory restaurantsViewModelFactory;
    private RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory;
    private WorkmatesViewModelFactory workmatesViewModelFactory;
    private SignInViewModelFactory signInViewModelFactory;
    private MainViewModelFactory mainViewModelFactory;
    private SharedViewModelFactory sharedViewModelFactory;
    private SearchViewModelFactory searchViewModelFactory;
    private SelectedRestaurantsViewModelFactory selectedRestaurantsViewModelFactory;
    private MapSelectedRestaurantsViewModelFactory mapSelectedRestaurantsViewModelFactory;
    // work actions
    private ShowNotificationsAction showNotificationsAction;

    // Cache
    private Cache cache;

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

    public synchronized VisitorGateway visitorGateway() { // TODO Private
        if(this.visitorGateway == null) {
            this.visitorGateway = new VisitorGatewayImpl(this.database());
        }
        return this.visitorGateway;
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

    private synchronized MyPositionGateway myPositionGateway() {
        if(this.myPositionGateway == null) {
            this.myPositionGateway = new InMemoryMyPositionGatewayImpl();
        }
        return this.myPositionGateway;
    }

    // Use cases
    private synchronized GetRestaurantsNearbyUseCase getRestaurantsNearbyUseCase() {
        if(this.getRestaurantsNearbyUseCase == null) {
            this.getRestaurantsNearbyUseCase = new GetRestaurantsNearbyUseCase(
                    restaurantGateway(),
                    visitorGateway()
            );
        }
        return this.getRestaurantsNearbyUseCase;
    }

    private synchronized GetDistanceFromMyPositionToRestaurantUseCase getDistanceFromMyPositionToRestaurantUseCase() {
        if(this.getDistanceFromMyPositionToRestaurantUseCase == null) {
            this.getDistanceFromMyPositionToRestaurantUseCase = new GetDistanceFromMyPositionToRestaurantUseCase(
                this.distanceGateway()
            );
        }
        return this.getDistanceFromMyPositionToRestaurantUseCase;
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
                    this.likeGateway(),
                    this.sessionGateway()
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

    private synchronized ReceiveNotificationsUseCase receiveNotificationsUseCase() {
        if(this.receiveNotificationsUseCase == null) {
            this.receiveNotificationsUseCase = new ReceiveNotificationsUseCase(
                    this.visitorGateway(),
                    this.sessionGateway());
        }
        return this.receiveNotificationsUseCase;
    }

    private synchronized SaveMyPositionUseCase saveMyPositionUseCase() {
        if(this.saveMyPositionUseCase == null) {
            this.saveMyPositionUseCase = new SaveMyPositionUseCase(this.myPositionGateway());
        }
        return this.saveMyPositionUseCase;
    }

    private synchronized GetMyPositionUseCase getMyPositionUseCase() {
        if(this.getMyPositionUseCase == null) {
            this.getMyPositionUseCase = new GetMyPositionUseCase(this.myPositionGateway());
        }
        return this.getMyPositionUseCase;
    }

    private synchronized SearchRestaurantUseCase searchRestaurantUseCase() {
        if(this.searchRestaurantUseCase == null) {
            this.searchRestaurantUseCase = new SearchRestaurantUseCase(this.restaurantGateway(), this.visitorGateway());
        }
        return this.searchRestaurantUseCase;
    }

    private synchronized FilterSelectedRestaurantsUseCase filterSelectedRestaurantsUseCase() {
        if(this.filterSelectedRestaurantsUseCase == null) {
            this.filterSelectedRestaurantsUseCase = new FilterSelectedRestaurantsUseCase(
                    this.getRestaurantsNearbyUseCase()
            );
        }
        return this.filterSelectedRestaurantsUseCase;
    }

    // Cache
    public synchronized Cache cache() {
        if(this.cache == null) {
            this.cache = new Cache();
        }
        return this.cache;
    }

    // View model factories

    public synchronized MapViewModelFactory mapViewModelFactory() {
        if(this.mapViewModelFactory == null) {
            this.mapViewModelFactory = new MapViewModelFactory(this.getRestaurantsNearbyUseCase(), this.searchRestaurantUseCase());
        }
        return this.mapViewModelFactory;
    }

    public synchronized RestaurantsViewModelFactory restaurantsViewModelFactory() {
        if(this.restaurantsViewModelFactory == null) {
            this.restaurantsViewModelFactory = new RestaurantsViewModelFactory(
                    this.getRestaurantsNearbyUseCase(),
                    this.searchRestaurantUseCase(),
                    this.getNumberOfLikesPerRestaurantUseCase(),
                    this.getDistanceFromMyPositionToRestaurantUseCase(),
                    this.timeProvider(),
                    this.dateProvider()
            );
        }
        return this.restaurantsViewModelFactory;
    }

    public synchronized RestaurantDetailsViewModelFactory restaurantDetailsViewModelFactory() {
        if(this.restaurantDetailsViewModelFactory == null) {
            this.restaurantDetailsViewModelFactory = new RestaurantDetailsViewModelFactory(
                    goForLunchUseCase(),
                    getRestaurantVisitorsUseCase(),
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

    public synchronized SharedViewModelFactory sharedViewModelFactory() {
        if(this.sharedViewModelFactory == null) {
            this.sharedViewModelFactory = new SharedViewModelFactory(
                    this.saveMyPositionUseCase(),
                    this.getMyPositionUseCase()
            );
        }
        return this.sharedViewModelFactory;
    }

    public synchronized SearchViewModelFactory searchViewModelFactory() {
        if(this.searchViewModelFactory == null) {
            this.searchViewModelFactory = new SearchViewModelFactory();
        }
        return this.searchViewModelFactory;
    }

    public synchronized SelectedRestaurantsViewModelFactory selectedRestaurantsViewModelFactory() {
        if(this.selectedRestaurantsViewModelFactory == null) {
            this.selectedRestaurantsViewModelFactory = new SelectedRestaurantsViewModelFactory(
                    this.filterSelectedRestaurantsUseCase(),
                    this.getNumberOfLikesPerRestaurantUseCase(),
                    this.getDistanceFromMyPositionToRestaurantUseCase()
            );
        }
        return this.selectedRestaurantsViewModelFactory;
    }

    public synchronized MapSelectedRestaurantsViewModelFactory mapSelectedRestaurantsViewModelFactory() {
        if(this.mapSelectedRestaurantsViewModelFactory == null) {
            this.mapSelectedRestaurantsViewModelFactory = new MapSelectedRestaurantsViewModelFactory(
                    this.filterSelectedRestaurantsUseCase()
            );
        }
        return this.mapSelectedRestaurantsViewModelFactory;
    }

    // Work actions
    public synchronized ShowNotificationsAction showNotificationsAction() {
        if(this.showNotificationsAction == null) {
            this.showNotificationsAction = new ShowNotificationsAction(
                    this,
                    this.receiveNotificationsUseCase()
            );
        }
        return this.showNotificationsAction;
    }

}

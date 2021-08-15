package com.android.go4lunch.read.businesslogic.gateways;

import com.android.go4lunch.read.businesslogic.usecases.RestaurantVO;
import com.android.go4lunch.read.businesslogic.usecases.model.Geolocation;

public interface WithLocationPermissionDecorator<T> {

    T decor(Geolocation myPosition, RestaurantVO restaurant);

}

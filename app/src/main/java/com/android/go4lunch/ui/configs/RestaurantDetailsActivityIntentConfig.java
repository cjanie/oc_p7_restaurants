package com.android.go4lunch.ui.configs;

import android.content.Context;
import android.content.Intent;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.RestaurantDetailsActivity;

public class RestaurantDetailsActivityIntentConfig {

    private static final String RESTAURANT_ID = "id";
    private static final String RESTAURANT_NAME = "name";
    private static final String RESTAURANT_ADDRESS = "address";
    private static final String RESTAURANT_PHONE = "phone";
    private static final String RESTAURANT_WEB_SITE = "webSite";
    private static final String RESTAURANT_PHOTO_URL = "photoUrl";

    public static Intent getIntent(
            Context context,
            String restaurantId,
            String restaurantName,
            String restaurantAddress,
            String restaurantPhone,
            String restaurantWebSite,
            String restaurantPhotoUrl
    ) {
        Intent intent = new Intent(context, RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ID, restaurantId);
        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_NAME, restaurantName);
        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ADDRESS, restaurantAddress);
        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHONE, restaurantPhone);
        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_WEB_SITE, restaurantWebSite);
        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHOTO_URL, restaurantPhotoUrl);
        return intent;
    }

    public static Restaurant getRestaurant(Intent intent) {
        String id = intent.getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ID);
        String name = intent.getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_NAME);
        String address = intent.getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ADDRESS);
        String photoUrl = intent.getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHOTO_URL);
        String phone = intent.getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHONE);
        String website = intent.getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_WEB_SITE);

        Restaurant restaurant = new Restaurant(name, address);
        restaurant.setId(id);
        restaurant.setPhotoUrl(photoUrl);
        restaurant.setPhone(phone);
        restaurant.setWebSite(website);
        return restaurant;
    }
}

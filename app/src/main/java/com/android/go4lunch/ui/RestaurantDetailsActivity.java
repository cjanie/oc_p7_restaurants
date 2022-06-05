package com.android.go4lunch.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.intentConfigs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.businesslogic.exceptions.NotFoundException;
import com.google.android.material.snackbar.Snackbar;

public class RestaurantDetailsActivity extends BaseActivity {

    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.restaurantDetailsViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getApplication()).restaurantDetailsViewModelFactory()
        ).get(RestaurantDetailsViewModel.class);

        setContentView(R.layout.activity_restaurant_details);

        // Data
        // RESTAURANT
        Restaurant restaurant;

        String id = getIntent().getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ID);
        String name = getIntent().getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_NAME);
        String address = getIntent().getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ADDRESS);
        String photoUrl = getIntent().getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHOTO_URL);
        String phone = getIntent().getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHONE);
        String website = getIntent().getStringExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_WEB_SITE);

        restaurant = new Restaurant(name, address);
        restaurant.setId(id);
        restaurant.setPhotoUrl(photoUrl);
        restaurant.setPhone(phone);
        restaurant.setWebSite(website);

        try {
            this.restaurantDetailsViewModel.setRestaurant(restaurant);
        } catch (NotFoundException e) {
            Toast.makeText(this, e.getClass().getName(), Toast.LENGTH_LONG);
        }
    }

}

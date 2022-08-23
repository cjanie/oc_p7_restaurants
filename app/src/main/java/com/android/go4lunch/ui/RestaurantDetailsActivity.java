package com.android.go4lunch.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.configs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.businesslogic.exceptions.NotFoundException;

public class RestaurantDetailsActivity extends BaseActivity {

    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportFragmentManager();

        this.restaurantDetailsViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getApplication()).restaurantDetailsViewModelFactory()
        ).get(RestaurantDetailsViewModel.class);

        setContentView(R.layout.activity_restaurant_details);

        // Data
        // RESTAURANT
        Restaurant restaurant = RestaurantDetailsActivityIntentConfig.getRestaurant(this.getIntent());

        try {
            this.restaurantDetailsViewModel.setRestaurant(restaurant);
        } catch (NotFoundException e) {
            Toast.makeText(this, e.getClass().getName(), Toast.LENGTH_LONG);
        }
    }

}

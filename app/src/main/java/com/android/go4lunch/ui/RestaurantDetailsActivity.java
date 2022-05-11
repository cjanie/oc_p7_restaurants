package com.android.go4lunch.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

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

        String id = getIntent().getStringExtra("id");
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String photoUrl = getIntent().getStringExtra("photoUrl");
        String phone = getIntent().getStringExtra("phone");
        String website = getIntent().getStringExtra("website");

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

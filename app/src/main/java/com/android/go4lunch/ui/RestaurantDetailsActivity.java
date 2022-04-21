package com.android.go4lunch.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.gateways_impl.Mock;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.ui.adapters.ListVisitorRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.usecases.exceptions.NotFoundException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailsActivity extends BaseActivity {

    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    @BindView(R.id.details_restaurant_pastille)
    FrameLayout pastille;

    @BindView(R.id.details_restaurant_image)
    ImageView restaurantImage;

    @BindView(R.id.details_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.details_restaurant_address)
    TextView restaurantAddress;

    @BindView(R.id.details_start)
    ImageView star;

    @BindView(R.id.button_call_container)
    ConstraintLayout buttonCall;

    @BindView(R.id.button_like_container)
    ConstraintLayout buttonLike;

    @BindView(R.id.button_website_container)
    ConstraintLayout buttonWebsite;

    @BindView(R.id.workmates_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.restaurantDetailsViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getApplication()).restaurantDetailsViewModelFactory()
        ).get(RestaurantDetailsViewModel.class);

        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);

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

        if(restaurant.getPhotoUrl() != null) {
            Glide.with(this.restaurantImage.getContext())
                    .load(restaurant.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(this.restaurantImage);
        }
        this.restaurantName.setText(restaurant.getName());
        this.restaurantAddress.setText(restaurant.getAddress());


        // VISITORS
        this.restaurantDetailsViewModel.getVisitors().observe(this, visitors -> {
            ListVisitorRecyclerViewAdapter adapter = new ListVisitorRecyclerViewAdapter(visitors);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

            this.pastille.setVisibility(visitors.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        });

        // IS THE CURRENT SELECTION

        this.restaurantDetailsViewModel.getIsTheCurrentSelection().observe(this, isTheCurrentSelection -> {
            this.star.setVisibility(isTheCurrentSelection ? View.VISIBLE : View.INVISIBLE);
        });


        // CLICK CALL
        this.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // CLICK LIKE
        this.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLike();
            }
        });

        this.buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void handleLike() {
        try {
            this.restaurantDetailsViewModel.handleLike();
            
        } catch (NotFoundException e) {
            this.handleError(e);
        }
    }

    private void handleError(Exception e) {
        Toast.makeText(this, e.getClass().getName() + " " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

}

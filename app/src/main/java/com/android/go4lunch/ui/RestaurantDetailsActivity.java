package com.android.go4lunch.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.models.Restaurant;
import com.android.go4lunch.ui.adapters.ListVisitorRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.usecases.exceptions.NotFoundException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class RestaurantDetailsActivity extends BaseActivity {

    private final String[] permissions = new String[] {Manifest.permission.CALL_PHONE};

    public final int requestCode = 155;

    private RestaurantDetailsViewModel restaurantDetailsViewModel;


    @BindView(R.id.button_go)
    FloatingActionButton buttonGo;

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
        requestCallPermission();

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
        });

        // IS THE CURRENT SELECTION
        this.restaurantDetailsViewModel.getIsTheCurrentSelection().observe(this, isTheCurrentSelection -> {
            if(isTheCurrentSelection) {
                this.buttonGo.setImageDrawable(getDrawable(R.drawable.ic_baseline_check_circle_24));
            } else {
                this.buttonGo.setImageDrawable(getDrawable(R.drawable.ic_baseline_add_task_24));
            }
        });
        this.restaurantDetailsViewModel.updateIsTheCurrentSelection();

        // IS FAVORITE
        try {
            this.restaurantDetailsViewModel.getIsOneOfTheUserFavoriteRestaurants().observe(this, isFavorite -> {
                if(isFavorite) {
                    this.star.setImageDrawable(this.getDrawable(R.drawable.ic_baseline_star_24));
                } else {
                    this.star.setImageDrawable(this.getDrawable(R.drawable.ic_baseline_star_border_24));
                }
            });
            this.restaurantDetailsViewModel.updateIsOneOfTheUserFavoriteRestaurants();
        } catch (NoWorkmateForSessionException e) {
            e.printStackTrace();
            this.handleError(e);
        }

        // set on Click Listeners
        this.buttonGo.setOnClickListener(view -> {
            handleGoForLunch();
        });

        this.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(restaurant.getPhone());
            }
        });

        this.buttonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click like %%%%%%%%%%");
                try {
                    restaurantDetailsViewModel.handleLike();
                } catch (NoWorkmateForSessionException e) {
                    e.printStackTrace();
                    handleError(e);
                }
            }
        });

        this.buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void handleGoForLunch() {
        try {
            this.restaurantDetailsViewModel.handleGoForLunch();
            
        } catch (NotFoundException e) {
            this.handleError(e);
        }
    }

    private void handleError(Exception e) {
        Toast.makeText(this, e.getClass().getName() + " " + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void requestCallPermission() {
        ActivityResultLauncher launcher = this.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if(isGranted) {
                        EasyPermissions.onRequestPermissionsResult(
                                this.requestCode,
                                this.permissions,
                                new int[]{PackageManager.PERMISSION_GRANTED},
                                this
                        );
                    } else {
                        EasyPermissions.onRequestPermissionsResult(
                                this.requestCode,
                                this.permissions,
                                new int[]{PackageManager.PERMISSION_DENIED},
                                this
                        );
                    }
                }
        );
        launcher.launch(this.permissions[0]);
    }


    @SuppressLint("MissingPermission")
    private void call(String phoneNumber) {
        // Control
        if(EasyPermissions.hasPermissions(this, this.permissions)) {
            if(phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                Toast.makeText(this, R.string.no_phone_number_available, Toast.LENGTH_LONG).show();
            }
        } else {
            // Demand permission if missing, explaining that a permission is needed in order to make phone call
            EasyPermissions.requestPermissions(
                    this,
                    this.getString(R.string.call_permission_rationale_text),
                    155,
                    this.permissions);
        }
    }

}

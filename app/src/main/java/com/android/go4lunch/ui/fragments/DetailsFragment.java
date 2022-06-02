package com.android.go4lunch.ui.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.BuildConfig;
import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.adapters.ListVisitorRecyclerViewAdapter;
import com.android.go4lunch.ui.utils.SettingsRationale;
import com.android.go4lunch.ui.utils.UsesPermission;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends UsesPermission {

    private ActivityResultLauncher launcher;

    private final String PERMISSION = Manifest.permission.CALL_PHONE;

    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    @BindView(R.id.details_restaurant_image)
    ImageView restaurantImage;

    @BindView(R.id.details_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.details_restaurant_address)
    TextView restaurantAddress;

    @BindView(R.id.details_start)
    ImageView star;

    @BindView(R.id.button_go)
    FloatingActionButton buttonGo;

    @BindView(R.id.button_call_container)
    ConstraintLayout buttonCall;

    @BindView(R.id.button_like_container)
    ConstraintLayout buttonLike;

    @BindView(R.id.button_website_container)
    ConstraintLayout buttonWebsite;

    @BindView(R.id.workmates_recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.launcher = this.createResultActivityLauncher();

        this.restaurantDetailsViewModel = new ViewModelProvider(this.getActivity()).get(RestaurantDetailsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, root);

        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurant();
        if(restaurant.getPhotoUrl() != null) {
            Glide.with(this.restaurantImage.getContext())
                    .load(restaurant.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(this.restaurantImage);
        }
        this.restaurantName.setText(restaurant.getName());
        this.restaurantAddress.setText(restaurant.getAddress());

        // IS THE CURRENT SELECTION
        this.restaurantDetailsViewModel.getIsTheCurrentSelection().observe(this.getActivity(), isTheCurrentSelection -> {
            if(isTheCurrentSelection) {
                this.buttonGo.setImageDrawable(this.getActivity().getDrawable(R.drawable.ic_baseline_check_circle_24));
            } else {
                this.buttonGo.setImageDrawable(this.getActivity().getDrawable(R.drawable.ic_baseline_add_task_24));
            }
        });
        this.restaurantDetailsViewModel.fetchIsTheCurrentSelectionToUpdateLiveData();

        // IS FAVORITE

        this.restaurantDetailsViewModel.getIsMarkedAsFavoriteLiveData().observe(this.getActivity(), isFavorite -> {
            if(isFavorite) {
                this.star.setImageDrawable(this.getActivity().getDrawable(R.drawable.ic_baseline_star_24));
            } else {
                this.star.setImageDrawable(this.getActivity().getDrawable(R.drawable.ic_baseline_star_border_24));
            }
        });
        this.restaurantDetailsViewModel.fetchIsMarkedAsFavoriteToUpdateLiveData();


        // VISITORS
        this.restaurantDetailsViewModel.getVisitorsLiveData().observe(this.getActivity(), visitors -> {
            ListVisitorRecyclerViewAdapter adapter = new ListVisitorRecyclerViewAdapter(visitors);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.setAdapter(adapter);
        });

        this.restaurantDetailsViewModel.fetchVisitorsToUpdateLiveData();


        // set on Click Listeners
        this.buttonGo.setOnClickListener(view ->
                handleGoForLunch()
        );

        this.buttonCall.setOnClickListener(view ->
                {
                    // requests and handles call permission
                    this.launcher.launch(this.PERMISSION);
                }

        );

        this.buttonLike.setOnClickListener(view ->
                handleLike()
        );

        this.buttonWebsite.setOnClickListener(view ->
                handleWebSite(restaurant.getWebSite())
        );

        return root;
    }

    private void handleCallPermissionIsGranted() {
        String phoneNumber = this.restaurantDetailsViewModel.getRestaurant().getPhone();
        if(phoneNumber != null && !phoneNumber.isEmpty()) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        } else {
            Toast.makeText(this.getActivity(), R.string.no_phone_number_available, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void handlePermissionIsGranted() {
        this.handleCallPermissionIsGranted();
    }

    @Override
    protected void goToSettings() {
        this.goToSettingsWithRationale(R.string.call_rationale, R.string.call_permission_rationale_text);
    }

    private void handleGoForLunch() {
        this.restaurantDetailsViewModel.handleGoForLunch();

    }

    private void handleLike() {
        this.restaurantDetailsViewModel.handleLike();
    }

    private void handleWebSite(String webSiteUrl) {
        if(webSiteUrl != null || !webSiteUrl.isEmpty()) {
            this.navigateToWebSite();
        }
    }

    private void navigateToWebSite() {
        Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment).navigate(R.id.action_detailsFragment_to_webViewFragment);
    }

    private void handleError(Exception e) {
        Toast.makeText(this.getActivity(), e.getClass().getName() + " " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}

package com.android.go4lunch.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.adapters.ListVisitorRecyclerViewAdapter;
import com.android.go4lunch.ui.configs.MyLunchPreferencesConfig;
import com.android.go4lunch.ui.configs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends UsesPermission {

    private ActivityResultLauncher launcher;

    private final String PERMISSION = Manifest.permission.CALL_PHONE;

    private RestaurantDetailsViewModel restaurantDetailsViewModel;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor preferencesEditor;

    @BindView(R.id.details_restaurant_image)
    ImageView restaurantImage;

    @BindView(R.id.details_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.details_restaurant_address)
    TextView restaurantAddress;

    @BindView(R.id.details_start)
    ImageView star;

    @BindView(R.id.button_go_unchecked)
    FloatingActionButton buttonGoUnchecked;

    @BindView(R.id.button_go_checked)
    FloatingActionButton buttonGoChecked;

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

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        this.preferencesEditor = sharedPreferences.edit();

        View root = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, root);

        Restaurant restaurant = this.restaurantDetailsViewModel.getRestaurant();
        if(restaurant.getPhotoUrl() != null) {
            Glide.with(this.restaurantImage.getContext())
                    .load(restaurant.getPhotoUrl())
                    .error(R.drawable.ic_baseline_error_24)
                    .into(this.restaurantImage);
        }
        this.restaurantName.setText(restaurant.getName());
        this.restaurantAddress.setText(restaurant.getAddress());

        // IS THE CURRENT SELECTION
        this.restaurantDetailsViewModel.getIsTheCurrentSelection().observe(this.getActivity(), isTheCurrentSelection -> {
            if(isTheCurrentSelection) {
                this.buttonGoChecked.setVisibility(View.VISIBLE);
                this.buttonGoUnchecked.setVisibility(View.GONE);
            } else {
                this.buttonGoChecked.setVisibility(View.GONE);
                this.buttonGoUnchecked.setVisibility(View.VISIBLE);
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
        this.buttonGoChecked.setOnClickListener(view -> {
                unselectRestaurant();
                preferencesEditor.putBoolean(MyLunchPreferencesConfig.IS_MY_LUNCH_SELECTED, false);
                preferencesEditor.commit();
            }
        );

        this.buttonGoUnchecked.setOnClickListener(view -> {
                selectRestaurant();
                preferencesEditor.putBoolean(MyLunchPreferencesConfig.IS_MY_LUNCH_SELECTED, true);
                preferencesEditor.putString(MyLunchPreferencesConfig.RESTAURANT_ID, this.restaurantDetailsViewModel.getRestaurant().getId());
                preferencesEditor.putString(MyLunchPreferencesConfig.RESTAURANT_NAME, this.restaurantDetailsViewModel.getRestaurant().getName());
                preferencesEditor.putString(MyLunchPreferencesConfig.RESTAURANT_ADDRESS, this.restaurantDetailsViewModel.getRestaurant().getAddress());
                preferencesEditor.putString(MyLunchPreferencesConfig.RESTAURANT_PHONE, this.restaurantDetailsViewModel.getRestaurant().getPhone());
                preferencesEditor.putString(MyLunchPreferencesConfig.RESTAURANT_PHOTO_URL, this.restaurantDetailsViewModel.getRestaurant().getPhotoUrl());
                preferencesEditor.putString(MyLunchPreferencesConfig.RESTAURANT_WEB_SITE, this.restaurantDetailsViewModel.getRestaurant().getWebSite());
                preferencesEditor.commit();
            }
        );

        this.buttonCall.setOnClickListener(view ->
                // requests and handles call permission
                this.launcher.launch(this.PERMISSION)
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

    private void selectRestaurant() {

        this.restaurantDetailsViewModel.selectRestaurant();
    }

    private void unselectRestaurant() {
        this.restaurantDetailsViewModel.unselectRestaurant();
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

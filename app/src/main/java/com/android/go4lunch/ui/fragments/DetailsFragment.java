package com.android.go4lunch.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.adapters.ListVisitorRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.RestaurantDetailsViewModel;
import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.businesslogic.exceptions.NotFoundException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class DetailsFragment extends Fragment {

    private ActivityResultLauncher launcher;

    private final String[] PERMISSIONS = new String[] {Manifest.permission.CALL_PHONE};

    private final int REQUEST_CODE = 155;

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
        View root = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, root);
        this.launcher = this.createActivityResultLauncher();
        this.restaurantDetailsViewModel = new ViewModelProvider(this.getActivity()).get(RestaurantDetailsViewModel.class);

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
        this.restaurantDetailsViewModel.updateIsTheCurrentSelectionLiveData();

        // IS FAVORITE
        try {
            this.restaurantDetailsViewModel.getIsOneOfTheUserFavoriteRestaurants().observe(this.getActivity(), isFavorite -> {
                if(isFavorite) {
                    this.star.setImageDrawable(this.getActivity().getDrawable(R.drawable.ic_baseline_star_24));
                } else {
                    this.star.setImageDrawable(this.getActivity().getDrawable(R.drawable.ic_baseline_star_border_24));
                }
            });
            this.restaurantDetailsViewModel.updateIsOneOfTheUserFavoriteRestaurants();
        } catch (NoWorkmateForSessionException e) {
            e.printStackTrace();
            this.handleError(e);
        }

        // VISITORS
        this.restaurantDetailsViewModel.getVisitors().observe(this.getActivity(), visitors -> {
            ListVisitorRecyclerViewAdapter adapter = new ListVisitorRecyclerViewAdapter(visitors);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            recyclerView.setAdapter(adapter);
        });

        // set on Click Listeners
        this.buttonGo.setOnClickListener(view ->
                handleGoForLunch()
        );

        this.buttonCall.setOnClickListener(view ->
                {
                    requestCallPermission();
                    handleCall();
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

    private ActivityResultLauncher createActivityResultLauncher() {
        ActivityResultLauncher launcher = this.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if(isGranted) {
                        EasyPermissions.onRequestPermissionsResult(
                                this.REQUEST_CODE,
                                this.PERMISSIONS,
                                new int[]{PackageManager.PERMISSION_GRANTED},
                                this
                        );
                    } else {
                        EasyPermissions.onRequestPermissionsResult(
                                this.REQUEST_CODE,
                                this.PERMISSIONS,
                                new int[]{PackageManager.PERMISSION_DENIED},
                                this
                        );
                    }
                }
        );
        return launcher;
    }

    private void requestCallPermission() {
        this.launcher.launch(this.PERMISSIONS[0]);
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(155)
    private void handleCall() {
        // Control
        if(EasyPermissions.hasPermissions(this.getActivity(), this.PERMISSIONS)) {
            String phoneNumber = this.restaurantDetailsViewModel.getRestaurant().getPhone();
            if(phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callIntent);
            } else {
                Toast.makeText(this.getActivity(), R.string.no_phone_number_available, Toast.LENGTH_LONG).show();
            }
        } else if(EasyPermissions.permissionPermanentlyDenied(this, this.PERMISSIONS[0])){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }



    private void handleGoForLunch() {
        try {
            this.restaurantDetailsViewModel.handleGoForLunch();
        } catch (NotFoundException e) {
            this.handleError(e);
        }
    }

    private void handleLike() {
        System.out.println("Click like %%%%%%%%%%");
        try {
            this.restaurantDetailsViewModel.handleLike();
        } catch (NoWorkmateForSessionException e) {
            e.printStackTrace();
            this.handleError(e);
        }
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

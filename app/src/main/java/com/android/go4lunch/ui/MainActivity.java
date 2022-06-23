package com.android.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.intentConfigs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.notifications.NotificationWorker;
import com.android.go4lunch.ui.viewmodels.SessionViewModel;

import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;


public class MainActivity extends BaseActivity {

    // DATA
    private SessionViewModel sessionViewModel;

    private Restaurant myLunch;

    // UI
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate data provider
        this.sessionViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getApplication()).mainViewModelFactory()
        ).get(SessionViewModel.class);

        // Instantiate UI
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Toolbar
        this.setSupportActionBar(toolbar);
        this.toolbar.setNavigationOnClickListener(view ->
                drawerLayout.open()
        );

        // Navigation view header showing session data
        try {
            this.sessionViewModel.getSession().observe(this, workmateSession -> {
                Glide.with(
                        (ImageView) navigationView.getHeaderView(0).findViewById(R.id.photo_session)
                )
                        .load(workmateSession.getUrlPhoto())
                        .apply(RequestOptions.circleCropTransform())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(
                                (ImageView) navigationView.getHeaderView(0).findViewById(R.id.photo_session)
                        );
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.name_session)).setText(workmateSession.getName());
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.email_session)).setText(workmateSession.getEmail());
            });
        } catch (NoWorkmateForSessionException e) {
            Toast.makeText(this, e.getClass().getCanonicalName(), Toast.LENGTH_LONG);
        }
        this.sessionViewModel.fetchSessionToUpdateLiveData();

        this.blurNavigationViewHeaderBackground();

        sessionViewModel.fetchMyLunchToUpdateLiveData();
        sessionViewModel.getMyLunch().observe(MainActivity.this, restaurant -> {
            this.myLunch = restaurant;
        });

        // Navigation view menu
        this.navigationView.setCheckedItem(R.id.your_lunch);
        this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.your_lunch) {
                    if(myLunch != null) {
                        Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ID, myLunch.getId());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_NAME, myLunch.getName());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_ADDRESS, myLunch.getAddress());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHONE, myLunch.getPhone());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_WEB_SITE, myLunch.getWebSite());
                        intent.putExtra(RestaurantDetailsActivityIntentConfig.RESTAURANT_PHOTO_URL, myLunch.getPhotoUrl());

                        startActivity(intent);
                    }

                }
                if(item.getItemId() == R.id.settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.logout) {
                    sessionViewModel.signOut();
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.close();
                return false; // corresponds to is checked
            }
        });

        // Notifications
        /*
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(OneTimeWorkRequest.from(NotificationWorker.class));

         */

    }

    @Override
    public void onBackPressed() {
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.on_back_pressed_disabled, Snackbar.LENGTH_LONG).show();
    }

    private void blurNavigationViewHeaderBackground() {
        BlurView blurView = this.navigationView.getHeaderView(0).findViewById(R.id.blur_layout);
        ViewGroup viewGroup = (ViewGroup) this.navigationView.getHeaderView(0);
        blurView.setupWith(viewGroup)
                .setBlurAlgorithm(new RenderScriptBlur(this));
    }


}
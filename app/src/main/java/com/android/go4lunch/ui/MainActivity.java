package com.android.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;

import com.android.go4lunch.businesslogic.entities.Restaurant;
import com.android.go4lunch.ui.configs.MyLunchPreferencesConfig;
import com.android.go4lunch.ui.fragments.SearchAutocompleteFragment;
import com.android.go4lunch.ui.configs.RestaurantDetailsActivityIntentConfig;
import com.android.go4lunch.ui.notifications.AlarmReceiver;
import com.android.go4lunch.ui.viewmodels.SessionViewModel;

import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import butterknife.BindView;
import butterknife.ButterKnife;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;


public class MainActivity extends BaseActivity {

    // DATA
    private SessionViewModel sessionViewModel;

    private Cache cache;

    // UI
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;


    // Alarm notifications
    private AlarmManager alarmManager;

    private PendingIntent alarmPendingIntent;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate data provider
        this.sessionViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getApplication()).mainViewModelFactory()
        ).get(SessionViewModel.class);

        this.cache = ((Launch)this.getApplication()).cache();
        //this.cache = new ViewModelProvider(this).get(Cache.class);
        Log.d("MainActivity", "cache instance n° " + this.cache.hashCode());

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.preferencesEditor = sharedPreferences.edit();

        // Instantiate UI
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Toolbar
        this.setSupportActionBar(this.toolbar);
        ActionBar actionBar = this.getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
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

        // Navigation view menu
        this.navigationView.setCheckedItem(R.id.your_lunch);
        this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.your_lunch) {
                    Boolean myLunch = sharedPreferences.getBoolean(MyLunchPreferencesConfig.IS_MY_LUNCH_SELECTED, false);
                    if(myLunch) {
                        String restaurantId = sharedPreferences.getString(MyLunchPreferencesConfig.RESTAURANT_ID, "");
                        String restaurantName = sharedPreferences.getString(MyLunchPreferencesConfig.RESTAURANT_NAME, "");
                        String restaurantAddress = sharedPreferences.getString(MyLunchPreferencesConfig.RESTAURANT_ADDRESS, "");
                        String restaurantPhone = sharedPreferences.getString(MyLunchPreferencesConfig.RESTAURANT_PHONE, "");
                        String restaurantWebSite = sharedPreferences.getString(MyLunchPreferencesConfig.RESTAURANT_WEB_SITE, "");
                        String restaurantPhotoUrl = sharedPreferences.getString(MyLunchPreferencesConfig.RESTAURANT_PHOTO_URL, "");
                        Intent intent = RestaurantDetailsActivityIntentConfig.getIntent(
                                MainActivity.this,
                                restaurantId,
                                restaurantName,
                                restaurantAddress,
                                restaurantPhone,
                                restaurantWebSite,
                                restaurantPhotoUrl
                        );
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

        this.checkNotificationsEnabled();
    }

    private void checkNotificationsEnabled() {
        Boolean notificationsEnabled = this.sharedPreferences.getBoolean(this.getString(R.string.key_pref_notifications), true);
        System.out.println("check notifications enabled : " + notificationsEnabled);
        if(notificationsEnabled) {
            this.enableAlarm();
        } else {
            this.cancelAlarm();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.checkNotificationsEnabled();
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

    private void enableAlarm() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            alarmPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            LocalDateTime localDateTime = LocalDateTime.of(2022, 7, 29, 11, 30);
            long time2epoch = localDateTime.toEpochSecond(ZoneOffset.UTC);
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    time2epoch,
                    AlarmManager.INTERVAL_DAY,
                    alarmPendingIntent
            );
        }
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        this.alarmPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if(this.alarmManager == null) {
            this.alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(this.alarmPendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                this.onSearchCalled();
                item.setVisible(false);
                return true;
            case R.id.action_home:
                this.onHomeCalled();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onSearchCalled() {
        this.showFragment(new SearchAutocompleteFragment());
        this.cache.setMode(Mode.SEARCH);
    }

    private void onHomeCalled() {
        this.cache.setMode(Mode.LIST);
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.toolbar_frame_layout, fragment);
        transaction.commit();
    }


}
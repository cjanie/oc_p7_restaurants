package com.android.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

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

import com.android.go4lunch.ui.viewmodels.MainViewModel;

import com.android.go4lunch.usecases.exceptions.NoWorkmateForSessionException;
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
    private MainViewModel mainViewModel;

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
        this.mainViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getApplication()).mainViewModelFactory()
        ).get(MainViewModel.class);

        // Instantiate UI
        this.setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Toolbar
        this.setSupportActionBar(toolbar);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        // Navigation view header showing session data
        try {
            this.mainViewModel.getSession().observe(this, workmateSession -> {
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

        this.blurNavigationViewHeaderBackground();

        // Navigation view menu
        this.navigationView.setCheckedItem(R.id.your_lunch);
        this.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.logout) {
                    mainViewModel.signOut();
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.close();
                return false; // corresponds to is checked
            }
        });
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
package com.android.go4lunch.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.android.go4lunch.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            //EditTextPreference searchNearbyRadiusPreference = this.findPreference(this.getText(R.string.key_pref_radius));
            ListPreference radius = this.findPreference(this.getText(R.string.key_pref_radius));

            this.getActivity().getPreferences(MODE_PRIVATE).getInt(this.getResources().getString(R.string.key_pref_radius), Integer.parseInt(radius.getValue()));

            SwitchPreferenceCompat notificationsPreference = this.findPreference(this.getText(R.string.key_pref_notifications));
            if(notificationsPreference.isChecked()) {
                this.getActivity().getPreferences(MODE_PRIVATE).getBoolean(this.getResources().getString(R.string.key_pref_notifications), true);
            } else {
                this.getActivity().getPreferences(MODE_PRIVATE).getBoolean(this.getResources().getString(R.string.key_pref_notifications), false);
            }
            // TODO something with this preference

        }
    }
}
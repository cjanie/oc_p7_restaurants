package com.android.go4lunch;

import android.content.SharedPreferences;

import com.android.go4lunch.ui.SettingsConfiguration;

public class SharedPreferencesListener implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(SettingsConfiguration.notifications)) {

        }

    }
}

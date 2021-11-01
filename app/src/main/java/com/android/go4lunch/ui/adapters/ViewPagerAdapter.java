package com.android.go4lunch.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.go4lunch.ui.fragments.ListRestaurantFragment;
import com.android.go4lunch.ui.fragments.MapRestaurantFragment;
import com.android.go4lunch.ui.fragments.WorkmateListFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    final int totalTabs = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MapRestaurantFragment();
            case 1:
                return new ListRestaurantFragment();
            case 2:
                return new WorkmateListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return this.totalTabs;
    }
}

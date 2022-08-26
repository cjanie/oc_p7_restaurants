package com.android.go4lunch.ui.adapters;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.go4lunch.R;
import com.android.go4lunch.ui.fragments.ListRestaurantFragment;
import com.android.go4lunch.ui.fragments.MapFragment;
import com.android.go4lunch.ui.fragments.MapRestaurantFragment;
import com.android.go4lunch.ui.fragments.WorkmateListFragment;
import com.android.go4lunch.ui.viewmodels.SharedViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerAdapter extends FragmentStateAdapter {

    final int totalTabs = 3;

    private SharedViewModel sharedViewModel;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, TabLayout tabLayout, ViewPager2 viewPager2, SharedViewModel sharedViewModel) {
        super(fragmentManager, lifecycle);

        viewPager2.setAdapter(this);

        // attach tabLayout to viewPager
        String[] tabsTexts = new String[] {
                tabLayout.getResources().getString(R.string.map_view),
                tabLayout.getResources().getString(R.string.list_view),
                tabLayout.getResources().getString(R.string.workmates)
        };
        Drawable[] tabsIcons = new Drawable[] {
                tabLayout.getContext().getDrawable(R.drawable.ic_baseline_map_24),
                tabLayout.getContext().getDrawable(R.drawable.ic_baseline_view_list_24),
                tabLayout.getContext().getDrawable(R.drawable.ic_baseline_group_24)
        };

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(tabsTexts[position]);
                    tab.setIcon(tabsIcons[position]);
                }).attach();

        this.sharedViewModel = sharedViewModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MapFragment(this.sharedViewModel);
            case 1:
                return new ListRestaurantFragment(this.sharedViewModel);
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

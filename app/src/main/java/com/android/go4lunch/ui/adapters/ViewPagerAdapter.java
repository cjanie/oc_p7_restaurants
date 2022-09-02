package com.android.go4lunch.ui.adapters;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.go4lunch.R;
import com.android.go4lunch.ui.fragments.ListFragment;
import com.android.go4lunch.ui.fragments.MapFragment;
import com.android.go4lunch.ui.fragments.WorkmateListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerAdapter extends FragmentStateAdapter {

    final int totalTabs = 3;

    private MapFragment mapFragment;

    private ListFragment listFragment;

    private WorkmateListFragment workmateListFragment;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, TabLayout tabLayout, ViewPager2 viewPager2) {
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

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if(this.mapFragment == null) {
                    this.mapFragment = new MapFragment();
                }
                return this.mapFragment;
            case 1:
                if(this.listFragment == null) {
                    this.listFragment = new ListFragment();
                }
                return this.listFragment;
            case 2:
                if(this.workmateListFragment == null) {
                    this.workmateListFragment = new WorkmateListFragment();
                }
                return this.workmateListFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return this.totalTabs;
    }
}

package com.android.go4lunch.ui.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WithFrameLayoutFragment extends Fragment {

    protected int frameLayoutId;

    protected void showFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getChildFragmentManager().beginTransaction();
        transaction.replace(this.frameLayoutId, fragment);
        transaction.commit();
    }

}

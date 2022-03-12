package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.go4lunch.R;
import com.android.go4lunch.ui.listeners.CallClickListener;
import com.android.go4lunch.ui.listeners.LikeClickListener;
import com.android.go4lunch.ui.listeners.WebsiteClickListener;

public class ButtonsBarFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buttons_bar, container, false);

        ButtonFragment buttonCall = new ButtonFragment(R.drawable.ic_baseline_call_24, R.string.call, new CallClickListener());
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.buttons_bar, buttonCall).commit();

        ButtonFragment buttonLike = new ButtonFragment(R.drawable.ic_baseline_star_24, R.string.like, new LikeClickListener());
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.buttons_bar, buttonLike).commit();

        ButtonFragment buttonWebSite = new ButtonFragment(R.drawable.ic_baseline_public_24, R.string.website, new WebsiteClickListener());
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.buttons_bar, buttonWebSite).commit();

        return root;
    }


}

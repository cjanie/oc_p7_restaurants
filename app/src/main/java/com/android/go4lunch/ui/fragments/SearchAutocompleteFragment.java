package com.android.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.go4lunch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAutocompleteFragment extends Fragment {

    @BindView(R.id.edit_text_search_place)
    EditText searchPlaceEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_autocomplete, container, false);
        ButterKnife.bind(this, root);
        return root;
    }
}

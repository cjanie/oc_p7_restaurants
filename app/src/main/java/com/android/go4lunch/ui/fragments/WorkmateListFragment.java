package com.android.go4lunch.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.Launch;
import com.android.go4lunch.R;
import com.android.go4lunch.ui.adapters.ListWorkmateRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.WorkmatesViewModel;
import com.android.go4lunch.usecases.exceptions.NotFoundException;

public class WorkmateListFragment extends Fragment {

    private WorkmatesViewModel workmatesViewModel;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.workmatesViewModel = new ViewModelProvider(
                this,
                ((Launch) this.getActivity().getApplication()).workmatesViewModelFactory()
        ).get(WorkmatesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_workmate_list, container, false);

        this.recyclerView = (RecyclerView) root;
        Context context = root.getContext();
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            this.workmatesViewModel.list().observe(WorkmateListFragment.this, workmates -> {
                ListWorkmateRecyclerViewAdapter adapter = new ListWorkmateRecyclerViewAdapter(workmates);
                this.recyclerView.setAdapter(adapter);
            });
        } catch (NotFoundException e) {
            Toast.makeText(this.getContext(), e.getClass().getName(), Toast.LENGTH_LONG).show();
        }
    }
}


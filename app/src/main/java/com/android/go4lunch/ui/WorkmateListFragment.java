package com.android.go4lunch.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.go4lunch.R;
import com.android.go4lunch.ui.adapters.ListWorkmateRecyclerViewAdapter;

public class WorkmateListFragment extends Fragment {

    private WorkmateViewModel workmateViewModel;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.workmateViewModel = new ViewModelProvider(this.getActivity()).get(WorkmateViewModel.class);

        View root = inflater.inflate(R.layout.fragment_workmate_list, container, false);

        this.recyclerView = (RecyclerView) root;
        Context context = root.getContext();
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.workmateViewModel.list().observe(WorkmateListFragment.this, workmates -> {
            ListWorkmateRecyclerViewAdapter adapter = new ListWorkmateRecyclerViewAdapter(workmates);
            this.recyclerView.setAdapter(adapter);
        });
    }
}

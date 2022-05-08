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
import com.android.go4lunch.models.Workmate;
import com.android.go4lunch.ui.adapters.ListWorkmateRecyclerViewAdapter;
import com.android.go4lunch.ui.viewmodels.WorkmatesViewModel;
import com.android.go4lunch.usecases.exceptions.NotFoundException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

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


        this.workmatesViewModel.getWorkmates().observe(this.getViewLifecycleOwner(), workmates -> {
            ListWorkmateRecyclerViewAdapter adapter = new ListWorkmateRecyclerViewAdapter(workmates);
            this.recyclerView.setAdapter(adapter);
        });


        return root;
    }

}


package com.android.go4lunch.repositories;

import android.util.Log;

import com.android.go4lunch.apiFirebase.entities.UserService;
import com.android.go4lunch.gateways.WorkMateQuery;
import com.android.go4lunch.gateways.WorkmateCommand;
import com.android.go4lunch.models.Workmate;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class WorkmateRepository implements WorkmateCommand {

    private String TAG = "WORKMATE REPOSITORY";

    private Observable<List<Workmate>> workmates;

    public WorkmateRepository() {
        this.workmates = Observable.just(new ArrayList<>());
        this.fetchUsers();
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() {
        return this.workmates;
    }

    @Override
    public void setWorkmates(@NonNull Observable<List<Workmate>> workmates) {
        this.workmates = workmates;
    }

    public void fetchUsers() {
        UserService userService = new UserService();
        userService.getUsers().addOnSuccessListener(task -> {
            if (task != null && !task.isEmpty()) {
                Log.d(TAG, "task fetch users index 0: " + task.get(0).getName());
                this.workmates = Observable.just(task);
            }
        }).addOnFailureListener(error -> {
            Log.e(TAG, "task fetch users error: " + error); //  PERMISSION_DENIED: Missing or insufficient permissions.
        });
    }
}

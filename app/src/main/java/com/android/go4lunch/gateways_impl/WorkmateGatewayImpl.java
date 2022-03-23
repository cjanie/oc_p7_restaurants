package com.android.go4lunch.gateways_impl;

import android.util.Log;

import com.android.go4lunch.apis.apiFirebase.UserRepository;
import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class WorkmateGatewayImpl implements WorkmateGateway {

    private String TAG = "WORKMATE GATEWAY IMPL";

    private Observable<List<Workmate>> workmates;



    public WorkmateGatewayImpl() {


        this.workmates = Observable.just(new Mock().workmates());
        //this.fetchUsers();
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
        UserRepository userRepository = new UserRepository();
        userRepository.getUsers().addOnSuccessListener(task -> {
            if (task != null && !task.isEmpty()) {
                this.workmates = Observable.just(task);
            }
        }).addOnFailureListener(error -> {
            Log.e(this.TAG, "task fetch users error: " + error); //  ex: PERMISSION_DENIED: Missing or insufficient permissions.
        });
    }

}

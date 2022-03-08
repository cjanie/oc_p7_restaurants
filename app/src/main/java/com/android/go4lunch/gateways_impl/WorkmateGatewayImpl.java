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
        Workmate janie = new Workmate("Janie");
        janie.setId("1");
        janie.setPhone("06 59 12 12 12");
        janie.setEmail("janie.chun@hotmail.fr");
        janie.setUrlPhoto("https://i.pravatar.cc/150?u=a042581f4e29026704d");

        List<Workmate> workmates = new ArrayList<>();
        workmates.add(janie);
        workmates.add(janie);
        this.workmates = Observable.just(workmates);
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

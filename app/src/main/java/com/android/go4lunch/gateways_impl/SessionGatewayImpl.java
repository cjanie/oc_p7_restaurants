package com.android.go4lunch.gateways_impl;

import android.util.Log;

import com.android.go4lunch.apis.apiFirebase.UserRepository;
import com.android.go4lunch.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class SessionGatewayImpl implements SessionGateway {

    private String TAG = "SessionRepository";

    private Observable<List<Workmate>> workmates;

    public SessionGatewayImpl() {
        this.workmates = Observable.just(new ArrayList<>());
        this.fetchSession();
    }

    @Override
    public Observable<Workmate> getWorkmate() {
        return this.workmates.flatMap(workmates -> {
            if(workmates.isEmpty()) {
                throw new NoWorkmateForSessionException();
            }
            Log.d(TAG, "get Workmate from session: " + workmates.get(0).getName());
            return Observable.just(workmates.get(0));
        });
    }

    private void fetchSession() {
        UserRepository userRepository = new UserRepository();
        userRepository.getAuthUser().addOnSuccessListener(task -> {
            this.workmates = Observable.just(Arrays.asList(task));
        });
    }
}

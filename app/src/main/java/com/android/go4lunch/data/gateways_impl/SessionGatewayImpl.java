package com.android.go4lunch.data.gateways_impl;

import com.android.go4lunch.businesslogic.exceptions.NoWorkmateForSessionException;
import com.android.go4lunch.businesslogic.gateways.SessionGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.models.WorkmateModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class SessionGatewayImpl implements SessionGateway {

    private String TAG = "Session gateway impl";

    private FirebaseAuth auth;

    private BehaviorSubject<Workmate> sessionSubject;

    public SessionGatewayImpl(FirebaseAuth auth) {
        this.auth = auth;
        this.sessionSubject = BehaviorSubject.create();
        this.fetchSessionToUpdateSubject();
    }

    @Override
    public void signOut() {
        this.auth.signOut();
    }

    @Override
    public Observable<Workmate> getSession()  {

        return this.sessionSubject
                .hide()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError(exception -> {
                    throw new NoWorkmateForSessionException();
                });
    }

    private void fetchSessionToUpdateSubject() {
        FirebaseUser authUser = this.auth.getCurrentUser();
        if(authUser != null) {
            Workmate session = new WorkmateModel().createWorkmate(
                    authUser.getUid(),
                    authUser.getDisplayName(),
                    authUser.getEmail(),
                    authUser.getPhotoUrl().toString()
            );

            this.sessionSubject.onNext(session);
        }
    }
}

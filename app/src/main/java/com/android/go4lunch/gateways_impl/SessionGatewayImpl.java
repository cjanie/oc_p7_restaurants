package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

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
    }

    @Override
    public void signOut() {
        this.auth.signOut();
    }

    @Override
    public Observable<Workmate> getSession()  {
        this.fetchSessionToUpdateSubject();
        return this.sessionSubject
                .hide()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void fetchSessionToUpdateSubject() {
        FirebaseUser authUser = this.auth.getCurrentUser();
        if(authUser != null) {
            Workmate session = new Workmate(authUser.getDisplayName());
            session.setId(authUser.getUid());
            session.setEmail(authUser.getEmail());
            session.setUrlPhoto(authUser.getPhotoUrl().toString());

            this.updateSessionSubject(session);
        }
    }

    private void updateSessionSubject(Workmate session) {
        this.sessionSubject.onNext(session);
    }
}

package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;

public class SessionGatewayImpl implements SessionGateway {

    private String TAG = "Session gateway impl";

    private FirebaseAuth auth;

    // No use of subject. Once user is authentified, observable is instanciated and authentified user does not change
    private Observable<Workmate> workmateSessionObservable;

    public SessionGatewayImpl(FirebaseAuth auth) {
        this.auth = auth;
    }

    @Override
    public Observable<Workmate> getSession()  {
        this.fetchSession();
        return this.workmateSessionObservable;
    }

    private void fetchSession() {
        FirebaseUser authUser = this.auth.getCurrentUser();
        if(authUser != null) {
            Workmate workmateSession = new Workmate(authUser.getDisplayName());
            workmateSession.setId(authUser.getUid());
            workmateSession.setEmail(authUser.getEmail());
            workmateSession.setUrlPhoto(authUser.getPhotoUrl().toString());

            this.workmateSessionObservable = Observable.just(workmateSession); // Observable Instantiation
        }
    }
}

package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.SessionGateway;
import com.android.go4lunch.models.Workmate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SessionGatewayImpl implements SessionGateway {

    private String TAG = "Session gateway impl";

    private PublishSubject<Workmate> workmateSessionSubject;

    public SessionGatewayImpl() {
        this.workmateSessionSubject = PublishSubject.create();
    }

    @Override
    public Observable<Workmate> getWorkmate()  {
        this.fetchSession();
        return this.workmateSessionSubject.hide();
    }

    private void fetchSession() {

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        if(authUser != null) {
            Workmate workmate = new Workmate(authUser.getDisplayName());
            workmate.setId(authUser.getUid());
            workmate.setEmail(authUser.getEmail());
            workmateSessionSubject.onNext(workmate);
            System.out.println(this.TAG + " " + workmate.getName() + " id: " + workmate.getId());
        }
    }
}

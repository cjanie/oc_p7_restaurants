package com.android.go4lunch.apis.apiFirebase;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import com.android.go4lunch.models.Workmate;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private final String TAG = "USER REPOSITORY";

    private FirebaseUser authUser;

    private static final String COLLECTION_NAME = "users";

    public UserRepository() {
        this.authUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(this.TAG, "Constructor: FirebaseAuth current auth user = " + this.authUser);
    }

    // Get the Collection Reference using FirebaseFireStore from Firebase SDK
    private CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create a User entity in Firestore from auth user's data
    public void updateUser() {
        if(this.authUser != null){
            // If the auth user already exists in Firestore, we get his data (username)
            Task<DocumentSnapshot> userData = this.getAuthUserData();
            // Then we update his data
            userData.addOnSuccessListener(documentSnapshot -> {
                Map<String, Object> map = new HashMap();
                map.put("name", this.authUser.getDisplayName());
                map.put("phoneNumber", this.authUser.getPhoneNumber());
                if(this.authUser.getPhotoUrl() != null) {
                    map.put("photoUri", this.authUser.getPhotoUrl().toString());
                }

                documentSnapshot.getReference().update(map);
            });
        }
    }

    // Get the auth User's data from Firestore if he exists
    private Task<DocumentSnapshot> getAuthUserData(){
        if(this.authUser.getUid() != null) {
            return this.getCollection().document(this.authUser.getUid()).get();
        } else {
            return null;
        }
    }

    public Task<List<Workmate>> getUsers() {
        Task<QuerySnapshot> querySnapshot = this.getCollection().get();
        return querySnapshot.continueWith(task -> {
            List<Workmate> workmates = new ArrayList<>();
            if(!task.getResult().getDocuments().isEmpty()) {
                for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                    String username = "";
                    if (documentSnapshot.contains("name")) {
                        username = (String) documentSnapshot.get("name");
                    }
                    Workmate workmate = new Workmate(username);
                    workmates.add(workmate);
                }
            }
            return workmates;
        });
    }

    public Task<Workmate> getAuthUser() {
        return this.getAuthUserData().continueWith(task -> {
            DocumentSnapshot documentSnapshot= task.getResult();
            String username = "";
            if (documentSnapshot.contains("name")) {
                username = (String) documentSnapshot.get("name");
            }
            Workmate workmate = new Workmate(username);
            return workmate;
        });
    }

}

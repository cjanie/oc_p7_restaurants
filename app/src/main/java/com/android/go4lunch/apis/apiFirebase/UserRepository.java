package com.android.go4lunch.apis.apiFirebase;

import android.annotation.SuppressLint;

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
import java.util.List;

public class UserRepository {

    private String TAG = "USER SERVICE";

    private FirebaseUser user;

    private static final String COLLECTION_NAME = "users";

    public UserRepository() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
    }

    // Get the Collection Reference using FirebaseFireStore from Firebase SDK
    private CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create User in Firestore
    public void createUser() {
        if(this.user != null){
            @SuppressLint("RestrictedApi")
            User userToCreate = new User.Builder(this.user.getProviderId(), this.user.getEmail())
                    .setName(this.user.getDisplayName())
                    .setPhoneNumber(this.user.getPhoneNumber())
                    //.setPhotoUri(Uri.parse(this.user.getPhotoUrl() != null ? this.user.getPhotoUrl().toString() : null))
                    .build();

            Task<DocumentSnapshot> userData = this.getUserData();
            // If the user already exist in Firestore, we get his data (username)
            userData.addOnSuccessListener(documentSnapshot -> {
                this.getCollection().document(this.user.getUid()).set(userToCreate);

            });
        }
    }

    // Get User Data from Firestore
    private Task<DocumentSnapshot> getUserData(){
        if(this.user.getUid() != null) {
            return this.getCollection().document(this.user.getUid()).get();
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

    public Task<Workmate> getUser() {
        return this.getUserData().continueWith(task -> {
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

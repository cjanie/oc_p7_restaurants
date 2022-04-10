package com.android.go4lunch.gateways_impl;

import android.util.Log;

import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

public class WorkmateGatewayImpl implements WorkmateGateway {

    private String TAG = "WORKMATE GATEWAY IMPL";

    private FirebaseFirestore database;

    private PublishSubject<List<Workmate>> subject;

    public WorkmateGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.subject = PublishSubject.create();
        this.fetchWorkmates();
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() { // Renommer prefixe subscribe ou suffixe observable
        return this.subject.hide();
    }

    private void updateWorkmates(List<Workmate> workmates) {
        this.subject.onNext(workmates);
    }

    private void fetchWorkmates() {

        this.database.collection("workmates").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    QuerySnapshot query = task.getResult();
                    List<DocumentSnapshot> docs = query.getDocuments();

                    List<Workmate> list = new ArrayList<>();
                    if(!docs.isEmpty()) {
                        for(DocumentSnapshot doc: docs) {
                            Workmate workmate = new Workmate((String) doc.getData().get("name"));
                            workmate.setId(doc.getId());
                            workmate.setEmail((String) doc.getData().get("email"));
                            workmate.setPhone((String) doc.getData().get("phone"));
                            workmate.setUrlPhoto((String) doc.getData().get("urlPhoto"));

                            list.add(workmate);
                        }
                    }


                    Log.d("list size get 0 get id", String.valueOf(list.get(0).getId()));
                    System.out.println("docs list size in firebase: " + docs.size());
                    updateWorkmates(list);
                }
            }
        });
    }

    public void addWorkmate(Workmate workmate) {
        Map<String, Object> workmateMap = new HashMap<>();
        workmateMap.put("name", workmate.getName());
        workmateMap.put("email", workmate.getEmail());
        workmateMap.put("phone", workmate.getPhone());
        workmateMap.put("urlPhoto", workmate.getUrlPhoto());
        this.database.collection("workmates").document(workmate.getEmail())
                .set(workmateMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


    public void deleteWorkmate(Workmate workmate) {
        this.database.collection("workmates").document(workmate.getEmail())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

}

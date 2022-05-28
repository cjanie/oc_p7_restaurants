package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.WorkmateGateway;
import com.android.go4lunch.businesslogic.entities.Workmate;
import com.android.go4lunch.businesslogic.models.WorkmateEntityModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class WorkmateGatewayImpl implements WorkmateGateway {

    private class WorkmateDatabaseConfig {

        public static final String COLLECTION_PATH = "workmates";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
        public static final String URL_PHOTO = "urlPhoto";

    }

    private String TAG = "WORKMATE GATEWAY IMPL";

    private FirebaseFirestore database;

    private BehaviorSubject<List<Workmate>> workmatesSubject;

    public WorkmateGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.workmatesSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() {
        this.fetchWorkmatesToUpdateSubject();
        return this.workmatesSubject
                .hide()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private void fetchWorkmatesToUpdateSubject() {

        this.database.collection(WorkmateDatabaseConfig.COLLECTION_PATH).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                List<Workmate> workmates = this.formatWorkmatesQuery(task.getResult());
                this.updateWorkmatesSubject(workmates);
            }
        });
    }

    private List<Workmate> formatWorkmatesQuery(QuerySnapshot query) {
        List<Workmate> workmates = new ArrayList<>();
        List<DocumentSnapshot> docs = query.getDocuments();
        if(!docs.isEmpty()) {
            for(DocumentSnapshot doc: docs) {
                Workmate workmate = new WorkmateEntityModel().createWorkmate(
                        doc.getId(),
                        (String) doc.getData().get(WorkmateDatabaseConfig.NAME),
                        (String) doc.getData().get(WorkmateDatabaseConfig.EMAIL),
                        (String) doc.getData().get(WorkmateDatabaseConfig.URL_PHOTO),
                        (String) doc.getData().get(WorkmateDatabaseConfig.PHONE)
                );
                workmates.add(workmate);
            }
        }
        return workmates;
    }

    private void updateWorkmatesSubject(List<Workmate> workmates) {
        this.workmatesSubject.onNext(workmates);
    }

    @Override
    public void saveWorkmate(Workmate workmate) {
        Map<String, Object> workmateMap = new HashMap<>();
        workmateMap.put(WorkmateDatabaseConfig.NAME, workmate.getName());
        workmateMap.put(WorkmateDatabaseConfig.EMAIL, workmate.getEmail());
        workmateMap.put(WorkmateDatabaseConfig.PHONE, workmate.getPhone());
        workmateMap.put(WorkmateDatabaseConfig.URL_PHOTO, workmate.getUrlPhoto());
        this.database.collection(WorkmateDatabaseConfig.COLLECTION_PATH).document(workmate.getId())
                .set(workmateMap)
                .addOnSuccessListener((Void aVoid) ->
                        Log.d(TAG, "DocumentSnapshot successfully written!")
                )
                .addOnFailureListener((Exception e) ->
                        Log.w(TAG, "Error writing document", e)
                );
    }

    public void deleteWorkmate(Workmate workmate) {
        this.database.collection(WorkmateDatabaseConfig.COLLECTION_PATH).document(workmate.getId())
                .delete()
                .addOnSuccessListener((Void aVoid) ->
                        Log.d(TAG, "DocumentSnapshot successfully deleted!")
                )
                .addOnFailureListener((Exception e) ->
                        Log.w(TAG, "Error deleting document", e)
                );
    }

}

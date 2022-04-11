package com.android.go4lunch.gateways_impl;

import android.util.Log;

import com.android.go4lunch.gateways.WorkmateGateway;
import com.android.go4lunch.models.Workmate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

class WorkmateDatabaseConfig {

    public static final String COLLECTION_PATH = "workmates";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String URL_PHOTO = "urlPhoto";

}

public class WorkmateGatewayImpl implements WorkmateGateway {

    private String TAG = "WORKMATE GATEWAY IMPL";

    private FirebaseFirestore database;

    private PublishSubject<List<Workmate>> workmatesSubject;

    public WorkmateGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.workmatesSubject = PublishSubject.create();
    }

    @Override
    public Observable<List<Workmate>> getWorkmates() { // Renommer prefixe subscribe ou suffixe observable
        this.fetchWorkmates();
        return this.workmatesSubject.hide();
    }

    private void updateWorkmates(List<Workmate> workmates) {
        this.workmatesSubject.onNext(workmates);
    }

    private void fetchWorkmates() {

        this.database.collection(WorkmateDatabaseConfig.COLLECTION_PATH).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> docs = query.getDocuments();

                List<Workmate> workmates = new ArrayList<>();
                if(!docs.isEmpty()) {
                    for(DocumentSnapshot doc: docs) {
                        Workmate workmate = new Workmate((String) doc.getData().get(WorkmateDatabaseConfig.NAME));
                        workmate.setId(doc.getId());
                        workmate.setEmail((String) doc.getData().get(WorkmateDatabaseConfig.EMAIL));
                        workmate.setPhone((String) doc.getData().get(WorkmateDatabaseConfig.PHONE));
                        workmate.setUrlPhoto((String) doc.getData().get(WorkmateDatabaseConfig.URL_PHOTO));

                        workmates.add(workmate);
                    }
                }
                updateWorkmates(workmates);
            }
        });
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
        this.database.collection(WorkmateDatabaseConfig.COLLECTION_PATH).document(workmate.getId())
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

package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.models.SelectionEntityModel;
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

public class VisitorGatewayImpl implements VisitorGateway {

    private class SelectionDatabaseConfig {

        public static final String COLLECTION_PATH = "selections";
        public static final String RESTAURANT_ID = "restaurantId";
        public static final String WORKMATE_ID = "workmateId";
        public static final String RESTAURANT_NAME = "restaurantName";
        public static final String WORKMATE_NAME = "workmateName";
        public static final String WORKMATE_URL_PHOTO = "workmateUrlPhoto";
    }

    private final String TAG = "VISITOR GATEWAY IMPL";

    private final FirebaseFirestore database;

    private final BehaviorSubject<List<Selection>> selectionsSubject;

    public VisitorGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.selectionsSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<List<Selection>> getSelections() {
        this.fetchSelectionsToUpdateSubject();
        return this.selectionsSubject
                .hide()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private void fetchSelectionsToUpdateSubject() {
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                List<Selection> selections = this.formatSelectionsQuery(task.getResult());
                this.selectionsSubject.onNext(selections);
            }
        });
    }

    private List<Selection> formatSelectionsQuery(QuerySnapshot query) {
        List<Selection> selections = new ArrayList<>();
        List<DocumentSnapshot> docs = query.getDocuments();
        if(!docs.isEmpty()) {
            for(DocumentSnapshot doc: docs) {
                selections.add(this.createSelection(doc));
            }
        }
        return selections;
    }

    @Override
    public void addSelection(Selection selection) {
        Map<String, Object> selectionMap = new HashMap<>();
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_ID, selection.getRestaurantId());
        selectionMap.put(SelectionDatabaseConfig.WORKMATE_ID, selection.getWorkmateId());
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_NAME, selection.getRestaurantName());
        selectionMap.put(SelectionDatabaseConfig.WORKMATE_NAME, selection.getWorkmateName());
        selectionMap.put(SelectionDatabaseConfig.WORKMATE_URL_PHOTO, selection.getWorkmateUrlPhoto());
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH)
                .document(selection.getId())
                .set(selectionMap)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error writing document", e)
                );
    }

    @Override
    public void removeSelection(String id) {
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH).document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                })
                .addOnFailureListener(e ->
                    Log.w(TAG, "Error deleting document", e)
                );
    }

    @Override
    public Observable<List<Selection>> getVisitors(String restaurantId) {
        List<Selection> visitors = new ArrayList<>();
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                QuerySnapshot query = task.getResult();
                List<DocumentSnapshot> docs = query.getDocuments();

                if(!docs.isEmpty()) {
                    for(DocumentSnapshot doc: docs) {

                        if(doc.getData() != null
                                && doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID) != null
                                && doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID).equals(restaurantId)
                        ) {
                            visitors.add(this.createSelection(doc));
                        }

                    }
                }
            }
        });

        return Observable.just(visitors).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Selection createSelection(DocumentSnapshot doc) {
        Selection selection = new SelectionEntityModel().createSelection(
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID),
                (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_ID),
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_NAME),
                (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_NAME),
                (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_URL_PHOTO),
                doc.getId()
        );
        return selection;
    }

}

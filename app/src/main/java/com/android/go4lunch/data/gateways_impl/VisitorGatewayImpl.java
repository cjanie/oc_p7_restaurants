package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.VisitorGateway;
import com.android.go4lunch.businesslogic.entities.Selection;
import com.android.go4lunch.businesslogic.models.SelectionModel;
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
        public static final String RESTAURANT_URL_PHOTO = "restaurantUrlPhoto";
        public static final String RESTAURANT_ADDRESS = "restaurantAddress";
        public static final String RESTAURANT_PHONE = "restaurantPhone";
        public static final String RESTAURANT_WEB_SITE = "restaurantWebSite";
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
                .doOnError(error -> {
                    Log.e(TAG, error.getMessage());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

    }

    private void fetchSelectionsToUpdateSubject() {
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        List<Selection> selections = this.formatSelectionsQuery(task.getResult());
                        this.selectionsSubject.onNext(selections);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, e.getMessage());
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
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_URL_PHOTO, selection.getRestaurantUrlPhoto());
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_ADDRESS, selection.getRestaurantAddress());
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_PHONE, selection.getRestaurantPhone());
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_WEB_SITE, selection.getRestaurantWebSite());


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

        return Observable.just(visitors)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private Selection createSelection(DocumentSnapshot doc) {
        Selection selection = new SelectionModel().createSelection(
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID),
                (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_ID),
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_NAME),
                (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_NAME),
                (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_URL_PHOTO),
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_URL_PHOTO),
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ADDRESS),
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_PHONE),
                (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_WEB_SITE),
                doc.getId()
        );
        return selection;
    }

}

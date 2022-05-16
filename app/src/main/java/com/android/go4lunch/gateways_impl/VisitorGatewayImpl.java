package com.android.go4lunch.gateways_impl;

import android.util.Log;

import com.android.go4lunch.gateways.VisitorGateway;
import com.android.go4lunch.models.Selection;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

class SelectionDatabaseConfig {

    public static final String COLLECTION_PATH = "selections";
    public static final String RESTAURANT_ID = "restaurantId";
    public static final String WORKMATE_ID = "workmateId";
    public static final String RESTAURANT_NAME = "restaurantName";
}

public class VisitorGatewayImpl implements VisitorGateway {

    private String TAG = "VISITOR GATEWAY IMPL";

    private FirebaseFirestore database;

    private BehaviorSubject selectionsSubject;

    public VisitorGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.selectionsSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<List<Selection>> getSelections() {
        this.fetchSelectionsToUpdateSubject();
        return this.selectionsSubject.hide();
    }

    private void fetchSelectionsToUpdateSubject() {
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                List<Selection> selections = this.formatSelectionsQuery(task.getResult());
                Log.d(TAG, "-- fetch selections -- size: " + selections.size());

                this.updateSelectionsSubject(selections);
            }
        });
    }

    private List<Selection> formatSelectionsQuery(QuerySnapshot query) {
        List<Selection> selections = new ArrayList<>();
        List<DocumentSnapshot> docs = query.getDocuments();
        if(!docs.isEmpty()) {
            for(DocumentSnapshot doc: docs) {
                Selection selection = new Selection(
                        (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID),
                        (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_ID)
                );
                selection.setId(doc.getId());
                selection.setRestaurantName((String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_NAME));
                selections.add(selection);
            }
        }
        return selections;
    }

    private void updateSelectionsSubject(List<Selection> selections) {
        this.selectionsSubject.onNext(selections);
    }

    @Override
    public void addSelection(Selection selection) {
        Map<String, Object> selectionMap = new HashMap<>();
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_ID, selection.getRestaurantId());
        selectionMap.put(SelectionDatabaseConfig.WORKMATE_ID, selection.getWorkmateId());
        selectionMap.put(SelectionDatabaseConfig.RESTAURANT_NAME, selection.getRestaurantName());
        this.database.collection(SelectionDatabaseConfig.COLLECTION_PATH).document(selection.getId())
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
                        if(doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID).equals(restaurantId)) {
                            Selection selection = new Selection(
                                    (String) doc.getData().get(SelectionDatabaseConfig.RESTAURANT_ID),
                                    (String) doc.getData().get(SelectionDatabaseConfig.WORKMATE_ID)
                            );
                            selection.setId(doc.getId());
                            visitors.add(selection);
                        }
                    }
                }
            }
        });
        System.out.println(TAG + "%%%%%%%%%%%%%%%%%%%% visitors for restaurant : " + visitors.size());
        return Observable.just(visitors);
    }

}

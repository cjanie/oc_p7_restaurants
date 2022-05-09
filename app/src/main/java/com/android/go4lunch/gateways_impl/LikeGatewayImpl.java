package com.android.go4lunch.gateways_impl;

import com.android.go4lunch.gateways.LikeGateway;
import com.android.go4lunch.models.Like;
//import com.google.api.core.ApiFuture;
//import com.google.cloud.firestore.WriteResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;

class LikeDatabaseConfig {
    public static final String COLLECTION_PATH = "likes";
    public static final String RESTAURANT_ID = "restaurantId";
    public static final String WORKMATE_ID = "workmateId";
}

public class LikeGatewayImpl implements LikeGateway {

    private FirebaseFirestore database;

    private Observable<List<Like>> likesObservable;

    public LikeGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.likesObservable = Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<List<Like>> getLikes() {
        this.fetchLikes();
        return this.likesObservable;
    }

    private void fetchLikes() {
        this.database.collection(LikeDatabaseConfig.COLLECTION_PATH).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                List<DocumentSnapshot> docs = querySnapshot.getDocuments();

                List<Like> likes = new ArrayList<>();
                if(!docs.isEmpty()) {
                    for(DocumentSnapshot doc: docs) {
                        Like like = new Like(
                                (String) doc.getData().get(LikeDatabaseConfig.RESTAURANT_ID),
                                (String) doc.getData().get(LikeDatabaseConfig.WORKMATE_ID)
                        );
                        like.setId(doc.getId());
                        likes.add(like);
                    }
                }
                this.likesObservable = Observable.just(likes);
            }
        });
    }

    @Override
    public boolean add(Like like) {
        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put(LikeDatabaseConfig.RESTAURANT_ID, like.getRestaurantId());
        likeMap.put(LikeDatabaseConfig.WORKMATE_ID, like.getWorkmateId());

        CollectionReference collectionReference = this.database.collection(LikeDatabaseConfig.COLLECTION_PATH);
        // Get new Id first
        String newDocId = collectionReference.getId();
        //ApiFuture<WriteResult> future = (ApiFuture<WriteResult>) collectionReference.document(newDocId).set(likeMap);
        return true;

        /*
        try {
            //future.get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

         */
    }
}

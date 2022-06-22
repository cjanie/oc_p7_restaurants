package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class LikeGatewayImpl implements LikeGateway {

    private class LikeDatabaseConfig {
        public static final String COLLECTION_PATH = "likes";
        public static final String RESTAURANT_ID = "restaurantId";
        public static final String WORKMATE_ID = "workmateId";
    }

    private final String TAG = "LIKE GATEWAY IMPL";

    private FirebaseFirestore database;

    private BehaviorSubject<List<Like>> likesSubject;

    public LikeGatewayImpl(FirebaseFirestore database) {
        this.database = database;
        this.likesSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<List<Like>> getLikes() {
        this.fetchLikesToUpdateSubject();
        return this.likesSubject
                .hide()
                .doOnError(error -> {
                    Log.e(TAG, error.getMessage());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());

    }

    private void fetchLikesToUpdateSubject() {
        this.database.collection(LikeDatabaseConfig.COLLECTION_PATH)
                .get()

                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        List<Like> likes = this.formatLikesQuery(task.getResult());
                        this.likesSubject.onNext(likes);
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                });
    }

    private List<Like> formatLikesQuery(QuerySnapshot query) {
        List<Like> likes = new ArrayList<>();
        List<DocumentSnapshot> docs = query.getDocuments();
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
        return likes;
    }

    @Override
    public void add(Like like) {

        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put(LikeDatabaseConfig.RESTAURANT_ID, like.getRestaurantId());
        likeMap.put(LikeDatabaseConfig.WORKMATE_ID, like.getWorkmateId());
        this.database.collection(LikeDatabaseConfig.COLLECTION_PATH)
                .add(likeMap)
                .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                })
                .addOnFailureListener(e ->
                        Log.w(TAG, "Error writing document", e)
                );
    }
}

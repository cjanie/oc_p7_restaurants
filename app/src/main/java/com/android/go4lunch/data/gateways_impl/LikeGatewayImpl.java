package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;


import com.android.go4lunch.data.firebase.exceptions.FirebaseException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError(error -> {
                    Log.e(TAG, error.getMessage());
                });

    }

    private void fetchLikesToUpdateSubject() {
        this.database.collection(LikeDatabaseConfig.COLLECTION_PATH)
                .get()

                .addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                List<Like> likes = this.formatLikesQuery(task.getResult());
                Log.d(TAG, " -- fetch likes -- size: " + likes.size());
                this.likesSubject.onNext(likes);
            }
            task.addOnFailureListener(e -> {
                Log.e(TAG, e.getMessage());
                try {
                    throw new FirebaseException();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
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
    public Observable<Boolean> add(Like like) {
        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put(LikeDatabaseConfig.RESTAURANT_ID, like.getRestaurantId());
        likeMap.put(LikeDatabaseConfig.WORKMATE_ID, like.getWorkmateId());
        Task<DocumentReference> task = this.database.collection(LikeDatabaseConfig.COLLECTION_PATH)
                .add(likeMap);
        return Observable.just(task.isSuccessful());
    }
}

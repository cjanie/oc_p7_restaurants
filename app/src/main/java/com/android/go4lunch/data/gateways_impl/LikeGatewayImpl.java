package com.android.go4lunch.data.gateways_impl;

import android.util.Log;

import com.android.go4lunch.businesslogic.gateways.LikeGateway;
import com.android.go4lunch.businesslogic.entities.Like;


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

class LikeDatabaseConfig {
    public static final String COLLECTION_PATH = "likes";
    public static final String RESTAURANT_ID = "restaurantId";
    public static final String WORKMATE_ID = "workmateId";
}

public class LikeGatewayImpl implements LikeGateway {

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
        List<Like> likesResult = new ArrayList<>();
        return this.likesSubject
                .hide()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS)
                .map(likes -> {
            Log.d(TAG, "-- getLikes mapping likesSubject -- size: " + likes.size());
            return likes;
        });
    }

    private void fetchLikesToUpdateSubject() {
        this.database.collection(LikeDatabaseConfig.COLLECTION_PATH)
                .get()
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                List<Like> likes = this.formatLikesQuery(task.getResult());
                Log.d(TAG, " -- fetch likes -- size: " + likes.size());
                this.updateLikesSubject(likes);
            }
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
    private void updateLikesSubject(List<Like> likes) {
        this.likesSubject.onNext(likes);
    }

    @Override
    public boolean add(Like like) {
        Map<String, Object> likeMap = new HashMap<>();
        likeMap.put(LikeDatabaseConfig.RESTAURANT_ID, like.getRestaurantId());
        likeMap.put(LikeDatabaseConfig.WORKMATE_ID, like.getWorkmateId());
        Task<DocumentReference> task = this.database.collection(LikeDatabaseConfig.COLLECTION_PATH)
                .add(likeMap);
        System.out.println(task.isSuccessful() + "%%%%%% Task is successfull");
        return task.isSuccessful();
    }
}

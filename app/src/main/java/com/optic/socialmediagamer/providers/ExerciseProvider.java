package com.optic.socialmediagamer.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.optic.socialmediagamer.models.Exercise;

public class ExerciseProvider {

    CollectionReference mCollection;

    public ExerciseProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("Exercises");
    }

    public Task<Void> save(Exercise exercise) {// ncruz 3 save entity
        return mCollection.document().set(exercise);
    }

    public Query getAll() {
        return mCollection.orderBy("timestamp", Query.Direction.DESCENDING);
    }

    public Query getExerciseByCategoryAndTimestamp(String category) {
        return mCollection.whereEqualTo("category", category).orderBy("timestamp", Query.Direction.DESCENDING);
    }

    public Query getExerciseByTitle(String title) {
        return mCollection.orderBy("title").startAt(title).endAt(title+'\uf8ff');
    }

    public Query getExerciseByUser(String id){
        return mCollection.whereEqualTo("idUser", id);
    }

    public Task<DocumentSnapshot> getExerciseById(String id) {
        return mCollection.document(id).get();
    }

    public Task<Void> delete(String id) {
        return mCollection.document(id).delete();
    }

}

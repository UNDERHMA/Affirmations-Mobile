package com.photo.affirmations.services;

import android.content.Context;

import androidx.room.Room;

import com.photo.affirmations.daos.AffirmationDao;
import com.photo.affirmations.database.AppDatabase;
import com.photo.affirmations.entities.Affirmation;

import java.util.List;

import io.reactivex.Single;

public class AffirmationService {

    private AppDatabase database;
    private AffirmationDao affirmationDao;
    private Context context;

    public AffirmationService(Context context) {
        this.context = context;
        this.database = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "Affirmations-Database").build();
        this.affirmationDao = database.affirmationDao();
    }

    public Single<List<Affirmation>> getAllAffirmations() {
        return affirmationDao.getAll();
    }

    public Single<Long> insertAffirmation(Affirmation affirmation) {
        return affirmationDao.insert(affirmation);
    }

    public Single<Integer> deleteAffirmation(Affirmation affirmation) {
        return affirmationDao.delete(affirmation);
    }

    public Single<Integer> updateAffirmation(Affirmation affirmation) {
        return affirmationDao.update(affirmation);
    }

    public Single<Integer> reorganizeAfterDelete(int positionDeleted) {
        return affirmationDao.reorganizeAfterDelete(positionDeleted);
    }

    public Single<Integer> moveUpAndCascadeAffirmation(int moveFrom, int moveTo) {
        return affirmationDao.moveUpAndCascade(moveFrom,moveTo);
    }

    public Single<Integer> moveDownAndCascadeAffirmation(int moveFrom, int moveTo) {
        return affirmationDao.moveDownAndCascade(moveFrom,moveTo);
    }
}

package com.munderhill.affirmation.services;

import android.content.Context;

import androidx.room.Room;

import com.munderhill.affirmation.daos.AffirmationDao;
import com.munderhill.affirmation.database.AppDatabase;
import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

import io.reactivex.Single;

public class AffirmationService {

    private AppDatabase database;
    private AffirmationDao affirmationDao;
    private Context context;

    public AffirmationService(Context context) {
        this.context = context;
        this.database = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "database-name").build();
        this.affirmationDao = database.affirmationDao();
    }

    public Single<List<Affirmation>> getAllAffirmations() {
        return affirmationDao.getAll();
    }

    public void insertAffirmation(Affirmation affirmation) {
        affirmationDao.insert(affirmation);
    }

    public void deleteAffirmation(Affirmation affirmation) {
        affirmationDao.delete(affirmation);
    }

    public void updateAffirmation(Affirmation affirmation) {
        affirmationDao.update(affirmation);
    }

    public void moveUpAndCascadeAffirmation(int moveFrom, int moveTo) {
        affirmationDao.moveUpAndCascade(moveFrom,moveTo);
    }

    public void moveDownAndCascadeAffirmation(int moveFrom, int moveTo) {
        affirmationDao.moveDownAndCascade(moveFrom,moveTo);
    }
}

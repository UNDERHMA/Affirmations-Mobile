package com.munderhill.affirmation.services;

import androidx.room.Room;

import com.munderhill.affirmation.daos.AffirmationDao;
import com.munderhill.affirmation.database.AppDatabase;
import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

public class AffirmationService {

    private AppDatabase database;
    private AffirmationDao affirmationDao;

    public AffirmationService() {
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        affirmationDao = database.affirmationDao();
    }

    public List<Affirmation> getAllAffirmations() {
        affirmationDao.getAll();
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
}

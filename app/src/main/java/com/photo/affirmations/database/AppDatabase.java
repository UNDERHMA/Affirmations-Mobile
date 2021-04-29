package com.photo.affirmations.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.photo.affirmations.daos.AffirmationDao;
import com.photo.affirmations.entities.Affirmation;

@Database(entities = {Affirmation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AffirmationDao affirmationDao();
}

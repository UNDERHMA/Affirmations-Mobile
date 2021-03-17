package com.munderhill.affirmation.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.munderhill.affirmation.daos.AffirmationDao;
import com.munderhill.affirmation.entities.Affirmation;

@Database(entities = {Affirmation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AffirmationDao affirmationDao();
}

package com.munderhill.affirmation.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

@Dao
public class AffirmationDao {
    @Query("SELECT * FROM Affirmation")
    List<Affirmation> getAll();

    @Insert
    void insert(Affirmation affirmation);

    @Delete
    void delete(Affirmation affirmation);

    @Update
    void update(Affirmation affirmation);

}

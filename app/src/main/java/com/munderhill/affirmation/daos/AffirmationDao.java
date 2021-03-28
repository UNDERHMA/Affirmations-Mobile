package com.munderhill.affirmation.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface AffirmationDao {

    @Query("SELECT * FROM Affirmation")
    public Single<List<Affirmation>> getAll();

    @Insert
    public Completable insert(Affirmation affirmation);

    @Delete
    public Completable delete(Affirmation affirmation);

    @Update
    public Completable update(Affirmation affirmation);

    @Query("UPDATE Affirmation SET affirmationId = " +
            "CASE affirmationId" +
            "   WHEN :moveFrom THEN :moveTo" +
            "   WHEN affirmationId >= :moveTo AND affirmationId < :moveFrom THEN affirmationId+1" +
            " END;")
    public Completable moveUpAndCascade(int moveFrom, int moveTo);

    @Query("UPDATE Affirmation SET affirmationId = " +
            "CASE affirmationId" +
            "   WHEN :moveFrom THEN :moveTo" +
            "   WHEN affirmationId <= :moveTo AND affirmationId > :moveFrom THEN affirmationId-1" +
            " END;")
    public Completable moveDownAndCascade(int moveFrom, int moveTo);


}

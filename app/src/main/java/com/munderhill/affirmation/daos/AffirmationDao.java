package com.munderhill.affirmation.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface AffirmationDao {

    @Query("SELECT * FROM Affirmation ORDER BY affirmation_order ASC")
    public Single<List<Affirmation>> getAll();

    @Insert
    public Single<Long> insert(Affirmation affirmation);

    @Delete
    public Single<Integer> delete(Affirmation affirmation);

    @Update
    public Single<Integer> update(Affirmation affirmation);

    @Query("UPDATE Affirmation SET affirmation_order = " +
            "CASE WHEN affirmation_order = :moveFrom THEN :moveTo" +
            "   WHEN affirmation_order >= :moveTo AND affirmation_order < :moveFrom THEN affirmation_order+1" +
            " ELSE affirmation_order " +
            " END;")
    public Single<Integer> moveUpAndCascade(int moveFrom, int moveTo);

    @Query("UPDATE Affirmation SET affirmation_order = " +
            "CASE WHEN affirmation_order = :moveFrom THEN :moveTo" +
            " WHEN affirmation_order <= :moveTo AND affirmation_order > :moveFrom THEN affirmation_order-1" +
            " ELSE affirmation_order " +
            " END;")
    public Single<Integer> moveDownAndCascade(int moveFrom, int moveTo);

    @Query("UPDATE Affirmation SET affirmation_order = " +
            "CASE WHEN affirmation_order > :positionDeleted THEN affirmation_order-1" +
            " ELSE affirmation_order " +
            " END;")
    public Single<Integer> reorganizeAfterDelete(int positionDeleted);


}

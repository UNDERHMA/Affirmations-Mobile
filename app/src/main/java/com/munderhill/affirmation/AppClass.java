package com.munderhill.affirmation;

import android.app.Application;

import com.munderhill.affirmation.entities.Affirmation;
import com.munderhill.affirmation.services.AffirmationService;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class AppClass extends Application {

    private AffirmationService affirmationService;
    private List<Affirmation> affirmationList;
    private int currentAffirmationIndex;

    @Override
    public void onCreate() {
        super.onCreate();
        this.affirmationService = new AffirmationService(getApplicationContext());
        getAffirmationList()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {this.affirmationList = result;});
        currentAffirmationIndex = 0;
    }

    public Single<List<Affirmation>> getAffirmationList() {
        return affirmationService.getAllAffirmations();
    }

    public Affirmation getAffirmationById(int index) {
        if(index < affirmationList.size()) return affirmationList.get(index);
        else return null;
    }

    public Single<Integer> deleteFromAffirmationList(int indexInList) {
        affirmationList.remove(indexInList);
        return affirmationService.deleteAffirmation(affirmationList.get(indexInList));
    }

    public Single<Long> insertIntoAffirmationList(Affirmation affirmation) {
        affirmationList.add(affirmation);
        return affirmationService.insertAffirmation(affirmation);
    }

    public Single<Integer> updateAffirmation(Affirmation affirmation, int index) {
        affirmationList.set(index,affirmation);
        return affirmationService.updateAffirmation(affirmation);
    }

    public Single<Integer> moveInAffirmationList(int indexInListFrom, int indexInListTo) {
       //Moves to correct position in list and cascades other items to appropriate position
        if(indexInListFrom > indexInListTo) {
            return affirmationService.moveUpAndCascadeAffirmation(indexInListFrom,indexInListTo);
        }
        else {
            return affirmationService.moveDownAndCascadeAffirmation(indexInListFrom,indexInListTo);
        }
    }
}

package com.munderhill.affirmation;

import android.app.Application;

import com.munderhill.affirmation.entities.Affirmation;
import com.munderhill.affirmation.services.AffirmationService;

import java.util.Collections;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class AppClass extends Application {

    private AffirmationService affirmationService;
    private List<Affirmation> affirmationList;
    private int currentAffirmationIndex;

    @Override
    public void onCreate() {
        super.onCreate();
        this.affirmationService = new AffirmationService(getApplicationContext());
        affirmationService.getAllAffirmations()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {this.affirmationList = result;});
        currentAffirmationIndex = 0;
    }

    public List<Affirmation> getAffirmationList() {
        return affirmationList;
    }

    public Affirmation getAffirmationById(int index) {
        if(index < affirmationList.size()) return affirmationList.get(index);
        else return null;
    }

    public void deleteFromAffirmationList(int indexInList) {
        affirmationService.deleteAffirmation(affirmationList.get(indexInList));
        affirmationList.remove(indexInList);
    }

    public void insertIntoAffirmationList(Affirmation affirmation) {
        affirmationService.insertAffirmation(affirmation);
        affirmationList.add(affirmation);
    }

    public void editAffirmationList(int indexInList) {
        affirmationService.updateAffirmation(affirmationList.get(indexInList));
        // Have to edit this somehow!!!!!!!!!!!!!!!!!
    }

    public void moveInAffirmationList(int indexInListFrom, int indexInListTo) {
       //
        if(indexInListFrom > indexInListTo) {
            affirmationService.moveUpAndCascadeAffirmation(indexInListFrom,indexInListTo);
        }
        else {
            affirmationService.moveDownAndCascadeAffirmation(indexInListFrom,indexInListTo);
        }
        Collections.swap(affirmationList,indexInListFrom,indexInListTo);
    }
}

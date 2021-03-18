package com.munderhill.affirmation.application;

import android.app.Application;
import android.content.Context;

import com.munderhill.affirmation.entities.Affirmation;
import com.munderhill.affirmation.services.AffirmationService;

import java.util.Collections;
import java.util.List;

public class AppClass extends Application {

    private AffirmationService affirmationService;
    private List<Affirmation> affirmationList;

    @Override
    public void onCreate() {
        super.onCreate();
        this.affirmationService = new AffirmationService(getApplicationContext());
        this.affirmationList = affirmationService.getAllAffirmations();
    }

    public List<Affirmation> getAffirmationList() {
        return affirmationList;
    }

    public void deleteFromAffirmationList(int indexInList) {
        affirmationService.deleteAffirmation(affirmationList.get(indexInList));
        affirmationList.remove(indexInList);
    }

    public void insertIntoAffirmationList(int indexInList) {
        affirmationService.insertAffirmation(affirmationList.get(indexInList));
        affirmationList.remove(indexInList);
    }

    public void editAffirmationList(int indexInList) {
        affirmationService.updateAffirmation(affirmationList.get(indexInList));
        affirmationList.remove(indexInList);
    }

    public void moveInAffirmationList(int indexInListFrom, int indexInListTo) {
       //
        affirmationService.
        Collections.swap(affirmationList,indexInListFrom,indexInListTo);
    }
}

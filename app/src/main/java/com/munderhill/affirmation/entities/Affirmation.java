package com.munderhill.affirmation.entities;

import android.media.Image;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Affirmation {
    @PrimaryKey
    public int affirmationId;
    public Image image;
    public String affirmationString;

    public int getAffirmationId() {
        return affirmationId;
    }

    public void setAffirmationId(int affirmationId) {
        this.affirmationId = affirmationId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getAffirmationString() {
        return affirmationString;
    }

    public void setAffirmationString(String affirmationString) {
        this.affirmationString = affirmationString;
    }
}

package com.munderhill.affirmation.entities;

import android.media.Image;
import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Affirmation")
public class Affirmation {
    @PrimaryKey
    private int affirmationId;
    @ColumnInfo(name = "image_URI")
    private String imageURI;
    @ColumnInfo(name = "affirmation_string")
    private String affirmationString;

    public Affirmation(){}

    public Affirmation(int affirmationId, Uri imageURI, String affirmationString) {
        this.affirmationId = affirmationId;
        this.imageURI = imageURI.toString();
        this.affirmationString = affirmationString;
    }

    public int getAffirmationId() {
        return affirmationId;
    }

    public void setAffirmationId(int affirmationId) {
        this.affirmationId = affirmationId;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getAffirmationString() {
        return affirmationString;
    }

    public void setAffirmationString(String affirmationString) {
        this.affirmationString = affirmationString;
    }
}

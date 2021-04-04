package com.munderhill.affirmation.entities;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

@Entity(tableName = "Affirmation")
public class Affirmation {
    @PrimaryKey
    private int affirmationId;
    @ColumnInfo(name = "image_to_save")
    private byte[] imageToSave;
    @ColumnInfo(name = "affirmation_string")
    private String affirmationString;

    public Affirmation(){}

    public Affirmation(int affirmationId, Bitmap imageToSave, String affirmationString) {
        this.affirmationId = affirmationId;
        // convert bitmap to byte array and store
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        imageToSave.compress(Bitmap.CompressFormat.PNG, 0, os);
        this.imageToSave = os.toByteArray();
        this.affirmationString = affirmationString;
    }

    public int getAffirmationId() {
        return affirmationId;
    }

    public void setAffirmationId(int affirmationId) {
        this.affirmationId = affirmationId;
    }

    public byte[] getImageToSave() {
        return imageToSave;
    }

    public void setImageToSave(byte[] imageToSave) {
        this.imageToSave = imageToSave;
    }

    public String getAffirmationString() {
        return affirmationString;
    }

    public void setAffirmationString(String affirmationString) {
        this.affirmationString = affirmationString;
    }
}

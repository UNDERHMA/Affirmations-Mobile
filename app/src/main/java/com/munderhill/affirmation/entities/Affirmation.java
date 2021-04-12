package com.munderhill.affirmation.entities;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

@Entity(tableName = "Affirmation")
public class Affirmation {
    @PrimaryKey(autoGenerate = true)
    private int affirmationId;
    @ColumnInfo(name = "image_to_save")
    private byte[] imageToSave;
    @ColumnInfo(name = "affirmation_string")
    private String affirmationString;
    @ColumnInfo(name = "affirmation_order")
    private int affirmationOrder;

    public Affirmation(){}

    public Affirmation(Bitmap bitmapToSave, String affirmationString, int affirmationOrder) {
        // convert bitmap to byte array and store
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmapToSave.compress(Bitmap.CompressFormat.PNG, 0, os);
        this.imageToSave = os.toByteArray();
        // set string and order
        this.affirmationString = affirmationString;
        this.affirmationOrder = affirmationOrder;
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

    public void setBitMapToSave(Bitmap bitmapToSave) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmapToSave.compress(Bitmap.CompressFormat.PNG, 0, os);
        this.imageToSave = os.toByteArray();
    }

    public String getAffirmationString() {
        return affirmationString;
    }

    public void setAffirmationString(String affirmationString) {
        this.affirmationString = affirmationString;
    }

    public int getAffirmationOrder() {
        return affirmationOrder;
    }

    public void setAffirmationOrder(int affirmationOrder) {
        this.affirmationOrder = affirmationOrder;
    }
}

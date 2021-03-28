package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.entities.Affirmation;

import java.io.File;
import java.sql.Timestamp;

public class AddOrEditAffirmationsActivity extends AppCompatActivity {

    private String affirmationString;
    private Uri imageURI;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_affirmations);
    }

    // Save button creates an Affirmation Entity, which is then saved
    // Create temp file on internal storage, store image there, pass getAbsolutePath() to room database.
    // When saving data, needs to query for amount of data available on device before storing.

    private void addPicture(View view){
        /* https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        ?page=1&tab=votes#tab-top */
        new AlertDialog.Builder(getApplicationContext())
                .setTitle("Add image")
                .setMessage("Take a photo with your camera, or select an existing photo from your gallery")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        // https://androidkennel.org/android-camera-access-tutorial/ CHECK LICENSE
                        public void onClick(DialogInterface cameraInterface, int id) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                            }
                            else{
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                    File fileDirectory = getApplicationContext().getDir("imageDir",Context.MODE_PRIVATE);
                                    imageURI = Uri.fromFile(new File(fileDirectory,"AffirmApp"
                                            + new Timestamp(System.currentTimeMillis()) +".jpg"));

                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                                    startActivityForResult(cameraIntent,100);
                                }
                            }
                        }
                })
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, 200);
                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(imageURI);
            }
        }
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(data.getData());
            }
        }
    }

    private String save(){
        if(imageURI.equals("")) return "an error occurred";
        AppClass appClass = (AppClass) getApplicationContext();
        appClass.insertIntoAffirmationList(
                new Affirmation(appClass.getAffirmationList().size(),imageURI,affirmationString));
        return "Affirmation saved";
    }


    /* https://stackoverflow.com/questions/17674634/saving-and-reading-bitmaps-images-from-internal-memory-in-android
    * Brijesh Thakur - showed example of saving an image to Bitmap.
    private String saveImage() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("images", Context.MODE_PRIVATE);
        File imageFilePath = new File(directory,"image" + new Timestamp(System.currentTimeMillis()) + ".jpg");
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(imageFilePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fo);
            imageURI = imageFilePath.getAbsolutePath();
        } catch(Exception e) {
            e.printStackTrace();
            return "image save failed";
        } finally {
            try {
                fo.close();
            } catch(IOException e) {
                e.printStackTrace();
                return "image save failed";
            }
        }
        return "image saved successfully";
    }*/
}

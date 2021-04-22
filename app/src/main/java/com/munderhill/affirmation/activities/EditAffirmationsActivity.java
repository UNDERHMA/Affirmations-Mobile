package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.entities.Affirmation;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EditAffirmationsActivity extends AppCompatActivity {

    private Affirmation affirmation;
    private AppClass appClassReference;
    private TextView editAffirmationText;
    private Uri imageURI;
    private ImageView imageView;
    private Bitmap imageToSave;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_affirmations_420_560dpi);
        appClassReference = (AppClass) getApplicationContext();
        imageView = (ImageView) findViewById(R.id.imageView);
        editAffirmationText = (TextView) findViewById((R.id.editAffirmationText));
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("affirmationNumber");
        setAffirmation(position);
    }

    public void addPicture(View view){
        /* https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        ?page=1&tab=votes#tab-top */
        new AlertDialog.Builder(EditAffirmationsActivity.this)
                .setTitle("Add image")
                .setMessage("Take a photo with your camera, or select an existing photo from your gallery")
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    // https://androidkennel.org/android-camera-access-tutorial/ CHECK LICENSE
                    public void onClick(DialogInterface cameraInterface, int id) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(EditAffirmationsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                        else{
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                File fileDirectory = new File(Environment.getExternalStorageDirectory()
                                        + File.separator +"affirmation_app_images");
                                fileDirectory.mkdir();
                                    /*-- https://stackoverflow.com/questions/38200282/android-
                                    os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed
                                    Pkosta */
                                imageURI = FileProvider.getUriForFile(getApplicationContext(),
                                        getPackageName() + ".provider",
                                        (new File(fileDirectory,"AffirmApp"
                                                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                                                + ".jpg")));
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
                .setNeutralButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void cancel(View view){
        Intent intent = new Intent(this, EditAffirmationsListActivity.class);
        startActivity(intent);
    }

    /* https://stackoverflow.com/questions/2507898/how-to-pick-an-image-from-gallery-sd-card-for-my-app
                siammii - Need to refactor
                ******************
                ******************
                ******************
                ******************
                ******************
                ******************
                 */
    private Bitmap decodeAndSize(Uri selectedImage) throws FileNotFoundException {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);
        // The new size we want to scale to
        final int REQUIRED_SIZE = 140;
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                try {
                    imageToSave = decodeAndSize(imageURI);
                    imageView.setImageBitmap(imageToSave);
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                }
            }
        }
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                imageURI = data.getData();
                try {
                    imageToSave = decodeAndSize(imageURI);
                    imageView.setImageBitmap(imageToSave);
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                }
            }
        }
    }

    private void setAffirmation(int affirmationNumberInput) {
        byte[] imageByteArray = appClassReference.getAffirmationById(affirmationNumberInput).getImageToSave();
        imageView.setImageBitmap(
                BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length)
        );
        editAffirmationText.setText(appClassReference.
                getAffirmationById(affirmationNumberInput).getAffirmationString());
        affirmation = appClassReference.getAffirmationList().get(affirmationNumberInput);
    }

    public void update(View view){
        AppClass appClass = (AppClass) getApplicationContext();
        if(imageURI != null) {
            try {
                affirmation.setBitMapToSave(decodeAndSize(imageURI));
            } catch (FileNotFoundException f) {
                f.printStackTrace();
            }
        }

        // for some reason, edit doesn't work and then crashes
        affirmation.setAffirmationString(editAffirmationText.getText().toString());
        Single<Integer> updateAffirmation = appClass.updateAffirmation(affirmation,position);
        Single<List<Affirmation>> reinitialize = appClassReference.initializeAffirmationList();
        Single.concat(updateAffirmation,reinitialize)
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {if(result instanceof List) {
                    appClassReference.setAffirmationList((List<Affirmation>) result);
                }});
        Intent intent = new Intent(this, EditAffirmationsListActivity.class);
        new AlertDialog.Builder(EditAffirmationsActivity.this)
                .setTitle("")
                .setMessage("Affirmation updated successfully.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
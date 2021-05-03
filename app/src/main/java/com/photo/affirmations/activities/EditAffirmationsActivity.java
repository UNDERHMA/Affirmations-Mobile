package com.photo.affirmations.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photo.affirmations.AppClass;
import com.photo.affirmations.R;
import com.photo.affirmations.entities.Affirmation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        // Set layout based on screen size
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp < 400) {
            setContentView(R.layout.activity_edit_affirmations_320ldpi_480mdpi_400ldpi);
        } else if (configuration.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.activity_edit_affirmations_xhdpi_landscape);
        } else {
            setContentView(R.layout.activity_edit_affirmations_420_560dpi);
        }
        // Set a few instance variables
        appClassReference = (AppClass) getApplicationContext();
        imageView = (ImageView) findViewById(R.id.imageView);
        editAffirmationText = (TextView) findViewById((R.id.editAffirmationText));
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("affirmationNumber");
        setAffirmation(position);
    }

    public void addPicture(View view){
        new AlertDialog.Builder(EditAffirmationsActivity.this)
                .setTitle("Add image")
                .setMessage("Take a photo with your camera, or select an existing photo from your gallery")
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    /* CC BY-SA 4.0 License, available in package folder. Code snippet changed for my use.
                       Serch https://stackoverflow.com/questions/38552144/how-get-permission-for-camera-in-android-specifically-marshmallow
                    */
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
                                /* CC BY-SA 4.0 License, available in package folder. Code snippet not changed for my use.
                                   Pkosta https://stackoverflow.com/questions/38200282/android-
                                            os-fileuriexposedexception-file-storage-emulated-0-test-txt-exposed */
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

    /* CC BY-SA 4.0 License, available in package folder. Code snippet changed for my use.
       siamii https://stackoverflow.com/questions/2507898/how-to-pick-an-image-from-gallery-sd-card-for-my-app */
    private Bitmap decodeSizeRotate(Uri selectedImage) throws FileNotFoundException, IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, options);
        final int REQUIRED_SIZE = 140;
        int width_tmp = options.outWidth, height_tmp = options.outHeight;
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
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, options2);
        /* CC BY-SA 4.0 License, available in package folder. Code snippet changed for my use.
       Jason Robinson https://stackoverflow.com/questions/14066038/why-does-an-image
           -captured-using-camera-intent-gets-rotated-on-some-devices-on-a */
        ExifInterface exifInterface = new ExifInterface(getContentResolver().openInputStream(selectedImage));
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);
        Matrix rotationMatrix = new Matrix();
        if(orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            rotationMatrix.postRotate(90);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    rotationMatrix, true);
        } else if(orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            rotationMatrix.postRotate(180);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    rotationMatrix, true);
        } else if(orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            rotationMatrix.postRotate(270);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                    rotationMatrix, true);
        } else {
            return bitmap;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                try {
                    imageToSave = decodeSizeRotate(imageURI);
                    imageView.setImageBitmap(imageToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                imageURI = data.getData();
                try {
                    imageToSave = decodeSizeRotate(imageURI);
                    imageView.setImageBitmap(imageToSave);
                } catch (Exception e) {
                    e.printStackTrace();
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
                affirmation.setBitMapToSave(decodeSizeRotate(imageURI));
            } catch (Exception e) {
                e.printStackTrace();
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
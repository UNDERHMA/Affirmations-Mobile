package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ListActivity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.entities.Affirmation;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;

public class AddAffirmationsActivity extends ListActivity {


    // NEED TO USE RECYLCERVIEW TO MAKE LIST TO EDIT AFFIRMATIONS


    private TextView addAffirmationText;
    private Uri imageURI;
    private ImageView imageView;
    private Bitmap imageToSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_affirmations);
        imageView = (ImageView) findViewById(R.id.imageView);
        addAffirmationText = (TextView) findViewById((R.id.addAffirmationText));
    }

    public void activateSaveButton(){
        // Activate Save Button if valid Data is present
        Button saveButton = (Button) findViewById(R.id.save);
        if(imageURI != null) saveButton.setEnabled(true);
    }

    // Save button creates an Affirmation Entity, which is then saved
    // Create temp file on internal storage, store image there, pass getAbsolutePath() to room database.
    // When saving data, needs to query for amount of data available on device before storing.

    public void addPicture(View view){
        /* https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        ?page=1&tab=votes#tab-top */
        new AlertDialog.Builder(AddAffirmationsActivity.this)
                .setTitle("Add image")
                .setMessage("Take a photo with your camera, or select an existing photo from your gallery")
                .setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                        // https://androidkennel.org/android-camera-access-tutorial/ CHECK LICENSE
                        public void onClick(DialogInterface cameraInterface, int id) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(AddAffirmationsActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
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
        Intent intent = new Intent(this, MainActivity.class);
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
                    // remove "tap to add image text" once image populates
                    TextView addImageText = (TextView) findViewById(R.id.addImageText);
                    addImageText.setVisibility(GONE);
                    // Activate Save Button if valid Data is present
                    activateSaveButton();
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
                    // remove "tap to add image text" once image populates
                    TextView addImageText = (TextView) findViewById(R.id.addImageText);
                    addImageText.setVisibility(GONE);
                    // Activate Save Button if valid Data is present
                    activateSaveButton();
                } catch (FileNotFoundException f) {
                    f.printStackTrace();
                }
            }
        }
    }

    public void save(View view){
        AppClass appClass = (AppClass) getApplicationContext();
        appClass.insertIntoAffirmationList(
                new Affirmation(appClass.getAffirmationList().size(),imageToSave,addAffirmationText.getText().toString())
                ).subscribeOn(Schedulers.io())
                .subscribe();
        Intent intent = new Intent(this, MainActivity.class);
        new AlertDialog.Builder(AddAffirmationsActivity.this)
                .setTitle("")
                .setMessage("Affirmation added successfully.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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

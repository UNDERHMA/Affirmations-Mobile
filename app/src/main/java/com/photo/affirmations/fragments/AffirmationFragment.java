package com.photo.affirmations.fragments;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.photo.affirmations.AppClass;
import com.photo.affirmations.R;

public class AffirmationFragment extends Fragment {

    private TextView affirmationText;
    private AppClass appClassReference;
    private ImageView imageView;
    private int position;
    private AdView adView;

    public AffirmationFragment() {}

    public AffirmationFragment(int position) {
        this.position = position;
    }

    /* Apache 2.0 License, available in package folder. Based off of the tutorial link below. Modified.
     https://developer.android.com/training/animation/screen-slide-2 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set layout based on screen size
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp < 400) {
            return (ViewGroup) inflater.inflate(
                    R.layout.affirmation_fragment_320ldpi_480mdpi_400ldpi, container, false);
        } else if (configuration.smallestScreenWidthDp >= 600) {
            return (ViewGroup) inflater.inflate(
                    R.layout.affirmation_fragment_xhdpi_landscape, container, false);
        } else {
            return (ViewGroup) inflater.inflate(
                    R.layout.affirmation_fragment_420_560dpi, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //data binding and setting ImageView / TextView
        appClassReference = (AppClass) getContext().getApplicationContext();
        imageView = (ImageView) getView().findViewById(R.id.imageView);
        affirmationText = (TextView) getView().findViewById(R.id.affirmationText);
        adView = getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        setAffirmation(position);
    }

    private void setAffirmation(int affirmationNumberInput) {
        byte[] imageByteArray = appClassReference.getAffirmationById(affirmationNumberInput).getImageToSave();
        imageView.setImageBitmap(
            BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length)
        );
        affirmationText.setText(appClassReference.
                getAffirmationById(affirmationNumberInput).getAffirmationString());
    }
}

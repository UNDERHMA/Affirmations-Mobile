package com.munderhill.affirmation.fragments;

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

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;

public class AffirmationFragment extends Fragment {

    private TextView affirmationText;
    private AppClass appClassReference;
    private ImageView imageView;
    private int position;

    public AffirmationFragment() {}

    public AffirmationFragment(int position) {
        this.position = position;
    }

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set layout based on screen size
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp < 400) {
            return (ViewGroup) inflater.inflate(
                    R.layout.affirmation_fragment_320ldpi_480mdpi_400ldpi, container, false);
        } else if (configuration.smallestScreenWidthDp > 800) {
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

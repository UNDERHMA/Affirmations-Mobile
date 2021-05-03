package com.photo.affirmations.fragments.demo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.photo.affirmations.R;

public class DemoFragment6 extends Fragment {

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
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
        TextView affirmationText = (TextView) getView().findViewById(R.id.affirmationText);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.example_3));
        affirmationText.setText(R.string.demo6);
    }
}
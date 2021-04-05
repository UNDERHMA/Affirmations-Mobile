package com.munderhill.affirmation.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;


public class EditAffirmationFragment extends Fragment {
    private TextView affirmationText;
    private AppClass appClassReference;
    private ImageView imageView;
    private int position;

    public EditAffirmationFragment() {}

    public EditAffirmationFragment(int position) {
        this.position = position;
    }

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.affirmation_fragment, container, false);
    }


    /*********
     *
     *
     * NEED TO WORK ON ADDING DELETE AND EDIT ABILITIES TO THIS FRAGMENT
     */

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
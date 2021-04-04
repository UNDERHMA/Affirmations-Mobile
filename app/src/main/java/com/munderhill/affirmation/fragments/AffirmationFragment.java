package com.munderhill.affirmation.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;

public class AffirmationFragment extends Fragment {

    private TextView affirmationText;
    private AppClass appClassReference;
    private ImageView imageView;

    public AffirmationFragment() {}

    public AffirmationFragment(int position) {
        //data binding and setting ImageView / TextView
        appClassReference = (AppClass) getContext();
        imageView = (ImageView) getView().findViewById(R.id.imageView);
        affirmationText = (TextView) getView().findViewById(R.id.affirmationText);
        setAffirmation(position);
    }

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.affirmation_fragment, container, false);
    }

    private void setAffirmation(int affirmationNumberInput) {
        imageView.setImageURI(
                Uri.parse(
                        appClassReference.getAffirmationById(affirmationNumberInput).getImageURI()
                )
        );
        affirmationText.setText(appClassReference.
                getAffirmationById(affirmationNumberInput).getAffirmationString());
    }
}

package com.munderhill.affirmation.fragments.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.munderhill.affirmation.R;

public class DemoFragment6 extends Fragment {

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.affirmation_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //data binding and setting ImageView / TextView
        ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
        TextView affirmationText = (TextView) getView().findViewById(R.id.affirmationText);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.snazzy));
        affirmationText.setText(R.string.demo6);
    }
}
package com.munderhill.affirmation.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.fragments.AffirmationFragment;

public class ViewAffirmationsActivity extends FragmentActivity {

    private int totalAffirmations;
    private AppClass appClassReference;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_affirmations);
        // initialize a few variables
        appClassReference = (AppClass) getApplicationContext();



        // THIS IS THE PROBLEM - Size is 0. Need to debug the adding of Affirmations to see why it is not working...



        totalAffirmations = appClassReference.getAffirmationList().size();
        /*data binding and setting ImageView / TextView
        imageView = (ImageView) findViewById(R.id.imageView);
        affirmationText = (TextView) findViewById(R.id.affirmationText);
        setAffirmation(affirmationNumber); */
        viewPager2 = findViewById(R.id.pager);
        fragmentStateAdapter = new AffirmationFragmentStateAdapter(this);
        viewPager2.setAdapter(fragmentStateAdapter);
    }

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    /*
    public void next() {
        if(affirmationNumber == totalAffirmations-1) return;
        affirmationNumber++;
        setAffirmation(affirmationNumber);
    }

    public void previous() {
        if(affirmationNumber == 0) return;
        affirmationNumber--;
        setAffirmation(affirmationNumber);
    }

    private void setAffirmation(int affirmationNumberInput) {
        imageView.setImageURI(
                Uri.parse(
                        appClassReference.getAffirmationById(affirmationNumberInput).getImageURI()
                )
        );
        affirmationText.setText(appClassReference.
                getAffirmationById(affirmationNumberInput).getAffirmationString());
    }*/


    private class AffirmationFragmentStateAdapter extends FragmentStateAdapter {

        public AffirmationFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new AffirmationFragment(position);
        }

        @Override
        public int getItemCount() {
            return totalAffirmations;
        }
    }

}

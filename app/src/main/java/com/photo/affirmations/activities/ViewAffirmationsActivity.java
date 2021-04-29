package com.photo.affirmations.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.photo.affirmations.AppClass;
import com.photo.affirmations.R;
import com.photo.affirmations.fragments.AffirmationFragment;

public class ViewAffirmationsActivity extends AppCompatActivity {

    private int totalAffirmations;
    private AppClass appClassReference;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter fragmentStateAdapter;

    // https://developer.android.com/training/animation/screen-slide-2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_view_affirmations_all);
        // initialize a few variables
        appClassReference = (AppClass) getApplicationContext();
        totalAffirmations = appClassReference.getAffirmationListSize();
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

    // takes user back to home when back button on top is clicked.
    @Override
    public boolean onSupportNavigateUp() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        return true;
    }

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

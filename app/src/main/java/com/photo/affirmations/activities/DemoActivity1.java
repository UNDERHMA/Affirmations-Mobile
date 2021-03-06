package com.photo.affirmations.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.photo.affirmations.R;
import com.photo.affirmations.fragments.demo.DemoFragment1;
import com.photo.affirmations.fragments.demo.DemoFragment2;
import com.photo.affirmations.fragments.demo.DemoFragment3;
import com.photo.affirmations.fragments.demo.DemoFragment4;
import com.photo.affirmations.fragments.demo.DemoFragment5;
import com.photo.affirmations.fragments.demo.DemoFragment6;

public class DemoActivity1 extends AppCompatActivity {

        private ViewPager2 viewPager2;
        private FragmentStateAdapter fragmentStateAdapter;

        /* Apache 2.0 License, available in package folder. Code snippet modified for my use case.
        https://developer.android.com/training/animation/screen-slide-2 */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setContentView(R.layout.activity_demo1_all);
            // initialize a few variables
            viewPager2 = findViewById(R.id.pager);
            fragmentStateAdapter = new DemoFragmentStateAdapter(this);
            viewPager2.setAdapter(fragmentStateAdapter);
        }

        /* Apache 2.0 License, available in package folder. Code snippet not modified.
        https://developer.android.com/training/animation/screen-slide-2 */
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

        private class DemoFragmentStateAdapter extends FragmentStateAdapter {

            public DemoFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
                super(fragmentActivity);
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new DemoFragment1();
                    case 1:
                        return new DemoFragment2();
                    case 2:
                        return new DemoFragment3();
                    case 3:
                        return new DemoFragment4();
                    case 4:
                        return new DemoFragment5();
                    case 5:
                        return new DemoFragment6();
                    default:
                        return new DemoFragment1();
                }
            }

            @Override
            public int getItemCount() {
                return 6;
            }
        }

}



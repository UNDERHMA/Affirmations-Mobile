package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.munderhill.affirmation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout based on screen size
        Configuration configuration = getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp < 400) {
            setContentView(R.layout.activity_main_320ldpi_480mdpi_400ldpi);
        } else if (configuration.smallestScreenWidthDp > 800) {
            setContentView(R.layout.activity_main_xhdpi_landscape);
        } else {
            setContentView(R.layout.activity_main_420_560dpi);
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.scotland);
    }

    public void addAffirmations(View view) {
        Intent intent = new Intent(this, AddAffirmationsActivity.class);
        startActivity(intent);
    }

    public void demo(View view) {
        Intent intent = new Intent(this, DemoActivity1.class);
        startActivity(intent);
    }

    public void editAffirmations(View view) {
        Intent intent = new Intent(this, EditAffirmationsListActivity.class);
        startActivity(intent);
    }

    public void viewAffirmations(View view) {
        Intent intent = new Intent(this, ViewAffirmationsActivity.class);
        startActivity(intent);
    }

}

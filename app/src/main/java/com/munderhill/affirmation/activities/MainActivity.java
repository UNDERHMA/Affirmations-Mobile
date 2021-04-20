package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.munderhill.affirmation.R;
import com.munderhill.affirmation.adapters.EditAffirmationsAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.scotland);
        setContentView(R.layout.activity_main);
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

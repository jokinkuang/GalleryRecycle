package com.jokin.galleryrecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Gallery;

import com.jokin.galleryrecycle.ecogallery.EcoGallery;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Gallery mGallery;
    private EcoGallery mEcoGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mGallery = (Gallery) findViewById(R.id.gallery);
        mEcoGallery = (EcoGallery) findViewById(R.id.ecogallery);

        mGallery.setAdapter(new ImageAdapter(this));
        mGallery.setUnselectedAlpha(1.0f);

        mEcoGallery.setAdapter(new ImageAdapter(this));
        mEcoGallery.setUnselectedAlpha(1.0f);

        if (Constants.EcoGalleryDemo) {
            mGallery.setVisibility(View.GONE);
        } else {
            mEcoGallery.setVisibility(View.GONE);
        }
    }

}

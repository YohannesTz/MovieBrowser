package com.yohannes.dev.app.moviebrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.yohannes.dev.app.moviebrowser.adapters.ViewPagerAdapter;
import com.yohannes.dev.app.moviebrowser.fragments.ComedyFragment;
import com.yohannes.dev.app.moviebrowser.fragments.DocumentaryFragment;
import com.yohannes.dev.app.moviebrowser.fragments.DramaFragment;
import com.yohannes.dev.app.moviebrowser.fragments.TvMovieFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ComedyFragment(), "Comedy");
        viewPagerAdapter.addFragment(new DocumentaryFragment(), "Docmentary");
        viewPagerAdapter.addFragment(new TvMovieFragment(), "TvMovie");
        viewPagerAdapter.addFragment(new DramaFragment(), "Drama");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
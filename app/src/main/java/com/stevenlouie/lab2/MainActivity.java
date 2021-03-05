package com.stevenlouie.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.container);
        setupPager(viewPager);
    }

    // used to setup the fragment manager to handle all fragments
    private void setupPager(ViewPager pager) {
        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new SignupFragment());
        pager.setAdapter(adapter);
    }

    // sets the current fragment to the one user has clicked on
    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }
}

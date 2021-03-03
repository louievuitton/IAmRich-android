package com.stevenlouie.lab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

//    private FragmentPageAdapter fragmentPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.deleteDatabase("user_db");
//        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupPager(viewPager);
    }

    private void setupPager(ViewPager pager) {
        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new SignupFragment());
        pager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }
}

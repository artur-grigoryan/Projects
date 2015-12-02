package com.example.fragment.adapters;

import com.example.fragment.LocationFragment;
import com.example.fragment.MotionFragment;
import com.example.fragment.OrientationFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabPagerAdapter extends FragmentPagerAdapter {
 
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new LocationFragment();
        case 1:
            // Games fragment activity
            return new MotionFragment();
        case 2:
            // Movies fragment activity
            return new OrientationFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}


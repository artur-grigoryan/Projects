package com.example.fragment;

import com.example.fragment.adapters.TabPagerAdapter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
ActionBar.TabListener {

	private ViewPager viewPager;
	private TabPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	TextView netView, gpsView, gyroscope, proximity, light, gyroView, proxyView, lightView;
	// Tab titles
	private String[] tabs = { "Location", "Motion", "Orientation" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Config.context=this;
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// 	Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
            .setTabListener(this));
		}
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			 
		    @Override
		    public void onPageSelected(int position) {
		        // on changing the page
		        // make respected tab selected
		        actionBar.setSelectedNavigationItem(position);
		    }
		 
		    @Override
		    public void onPageScrolled(int arg0, float arg1, int arg2) {
		    }
		 
		    @Override
		    public void onPageScrollStateChanged(int arg0) {
		    }
		});

	}
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	// TODO Auto-generated method stub
	
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		// on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
	
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
	
	}
	
	
	
	

}

